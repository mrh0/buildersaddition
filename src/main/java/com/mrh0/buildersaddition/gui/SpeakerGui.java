package com.mrh0.buildersaddition.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.config.Config;
import com.mrh0.buildersaddition.container.SpeakerContainer;
import com.mrh0.buildersaddition.midi.IMidiEvent;
import com.mrh0.buildersaddition.network.PlayNotePacket;
import com.mrh0.buildersaddition.network.UpdateDataPacket;
import com.mrh0.buildersaddition.tileentity.SpeakerTileEntity;
import com.mrh0.buildersaddition.util.Notes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SpeakerGui extends AbstractContainerScreen<SpeakerContainer> implements IMidiEvent {

	private final SpeakerContainer screenContainer;
	private final SpeakerTileEntity te;

	private static final int SIZE = 16;

	private Button connectBtn;
	private Button helpBtn;

	private Button[] btns;

	public SpeakerGui(SpeakerContainer screenContainer, Inventory inv, Component tc) {
		super(screenContainer, inv, tc);
		this.screenContainer = screenContainer;
		this.te = (SpeakerTileEntity) Minecraft.getInstance().level.getBlockEntity(screenContainer.pos);

		this.imageWidth = 384;
		this.imageHeight = 192;

		if (BuildersAddition.midi != null)
			BuildersAddition.midi.midiEvent = this;
	}
	
	@Override
	protected void init() {
		super.init();
		
		int x = this.width / 2;// width
		int y = this.height / 2;// height
		
		OnPress p = (b) -> {
			
		};

		connectBtn = new Button(x - 48 - 12, y + 24 * 4, 96, 20,
				new TranslatableComponent(BuildersAddition.midi == null ? "container.buildersaddition.speaker.connect" : "container.buildersaddition.speaker.disconnect"), (b) -> {
					if (BuildersAddition.midi != null) {
						if (BuildersAddition.midi.midiEvent == null)
							BuildersAddition.midi.midiEvent = this;
						else
							BuildersAddition.midi.midiEvent = null;

						connectBtn.setMessage(new TranslatableComponent(
								BuildersAddition.midi.midiEvent == null ? "container.buildersaddition.speaker.connect" : "container.buildersaddition.speaker.disconnect"));// SetMessage
					}
				});
		
		helpBtn = new Button(x + 52 - 12, y + 24 * 4, 20, 20, new TextComponent("?"), (b) -> {});

		this.addRenderableWidget(connectBtn); //addButton
		this.addRenderableWidget(helpBtn); //addButton

		btns = new Button[SIZE];

		for (int i = 0; i < SIZE; i++) {
			btns[i] = new Button(x + (i > 7 ? -100 : 4), y + (i % 8 * 24) - 4 * 24, 96, 20,
					new TranslatableComponent("note.buildersaddition." + Notes.instrumentNames[i]), p);
			this.addRenderableWidget(btns[i]);// addButton
			btns[i].active = te.isInstrumentActive(i);// active
		}
	}
	
	
	
	@Override
	public void resize(Minecraft mc, int w, int h) {
		super.resize(mc, w, h);
	}

	// mouseClicked
	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		for (int i = 0; i < SIZE; i++) {
			if (btns[i].isMouseOver(p_mouseClicked_1_, p_mouseClicked_3_))// isHovered
				buttonClicked(btns[i], i);
		}

		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}

	private void buttonClicked(Button b, int id) {
		b.active = !b.active;// active
		sendInstrumentUpdate(getEncoded());
	}

	private int getEncoded() {
		int r = 0;
		for (int i = 0; i < SIZE; i++) {
			r += btns[i].active ? Math.pow(2, i) : 0;
		}
		return r;
	}

	private void sendInstrumentUpdate(int data) {
		if (getTE() != null)
			BuildersAddition.Network.sendToServer(new UpdateDataPacket(this.getTE().getBlockPos(), data));
	}

	// render
	@Override
	public void render(PoseStack stack, int x, int y, float partialTicks) {
		super.render(stack, x, y, partialTicks);
		//GlStateManager._disableLighting();
		if(btns == null)
			return;
		GlStateManager._disableBlend();

		for (int i = 0; i < SIZE; i++) {
			if (btns[i].isMouseOver(x, y))// isHovered
				renderTooltip(stack, new TextComponent("F#" + Notes.octaveNames[i]), x, y);
		}
		if(helpBtn.isMouseOver(x, y))
			renderTooltip(stack, new TextComponent(
					(hasDevice() ? "Device Discovered" : "No Device Connected") 
					+ ", Midi Input: " + (Config.MIDI_INPUT_ENABLED.get() ? "Enabled" : "Disabled")), x, y);
		this.renderTooltip(stack, x, y);
	}
	
	
	
	private boolean hasDevice() {
		if(BuildersAddition.midi != null)
			return BuildersAddition.midi.hasDevice();
		return false;
	}

	public BlockEntity getTE() {
		return te;
	}

	private void sendNote(int note) {
		if (getTE() != null)
			BuildersAddition.Network.sendToServer(new PlayNotePacket(this.getTE().getBlockPos(), note));
	}

	@Override
	public void minecraftNote(int note, boolean on) {
		note += 24;
		if (note < 0)
			return;
		if (on)
			sendNote(note);
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int x, int y) {
		renderBackground(stack);//renderBackground
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
		//super.renderLabels(p_97808_, p_97809_, p_97810_);
	}
}