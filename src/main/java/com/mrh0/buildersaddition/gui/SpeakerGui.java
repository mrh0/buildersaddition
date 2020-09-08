package com.mrh0.buildersaddition.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.config.Config;
import com.mrh0.buildersaddition.container.SpeakerContainer;
import com.mrh0.buildersaddition.midi.IMidiEvent;
import com.mrh0.buildersaddition.network.PlayNotePacket;
import com.mrh0.buildersaddition.network.UpdateDataPacket;
import com.mrh0.buildersaddition.tileentity.SpeakerTileEntity;
import com.mrh0.buildersaddition.util.Notes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SpeakerGui extends ContainerScreen<SpeakerContainer> implements IMidiEvent {

	private final SpeakerContainer screenContainer;
	private final SpeakerTileEntity te;

	private static final int SIZE = 16;

	private Button connectBtn;
	private Button helpBtn;

	private Button[] btns;

	public SpeakerGui(SpeakerContainer screenContainer, PlayerInventory inv, ITextComponent tc) {
		super(screenContainer, inv, tc);
		this.screenContainer = screenContainer;
		this.te = (SpeakerTileEntity) Minecraft.getInstance().world.getTileEntity(screenContainer.pos);

		this.xSize = 384;
		this.ySize = 192;

		if (BuildersAddition.midi != null)
			BuildersAddition.midi.midiEvent = this;
	}

	protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
		//this.field_230712_o_.func_238422_b_(p_230451_1_, this.field_230704_d_, (float) this.field_238742_p_,(float) this.field_238743_q_, 4210752);
	}

	// init
	@Override
	public void init(Minecraft p_231158_1_, int p_231158_2_, int p_231158_3_) {
		super.init(p_231158_1_, p_231158_2_, p_231158_3_);
		int x = this.width / 2;// width
		int y = this.height / 2;// height

		IPressable p = (b) -> {
		};

		connectBtn = new Button(x - 48 - 12, y + 24 * 4, 96, 20,
				new TranslationTextComponent(BuildersAddition.midi == null ? "container.buildersaddition.speaker.connect" : "container.buildersaddition.speaker.disconnect"), (b) -> {
					if (BuildersAddition.midi != null) {
						if (BuildersAddition.midi.midiEvent == null)
							BuildersAddition.midi.midiEvent = this;
						else
							BuildersAddition.midi.midiEvent = null;

						connectBtn.setMessage(new TranslationTextComponent(
								BuildersAddition.midi.midiEvent == null ? "container.buildersaddition.speaker.connect" : "container.buildersaddition.speaker.disconnect"));// SetMessage
					}
				});
		
		helpBtn = new Button(x + 52 - 12, y + 24 * 4, 20, 20, new StringTextComponent("?"), (b) -> {});

		this.addButton(connectBtn);// addButton
		this.addButton(helpBtn);// addButton

		btns = new Button[SIZE];

		for (int i = 0; i < SIZE; i++) {
			btns[i] = new Button(x + (i > 7 ? -100 : 4), y + (i % 8 * 24) - 4 * 24, 96, 20,
					new TranslationTextComponent("note.buildersaddition." + Notes.instrumentNames[i]), p);
			this.addButton(btns[i]);// addButton
			btns[i].active = te.isInstrumentActive(i);// active
		}
	}

	// mouseClicked
	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		for (int i = 0; i < SIZE; i++) {
			if (btns[i].isHovered())// isHovered
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
			BuildersAddition.Network.sendToServer(new UpdateDataPacket(this.getTE().getPos(), data));
	}

	// render
	@Override
	public void render(MatrixStack stack, int x, int y, float p_230430_4_) {
		super.render(stack, x, y, p_230430_4_);
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();

		for (int i = 0; i < SIZE; i++) {
			if (btns[i].isHovered())// isHovered
				renderTooltip(stack, new StringTextComponent("F#" + Notes.octaveNames[i]), x, y);
		}
		if(helpBtn.isHovered())
			renderTooltip(stack, new StringTextComponent(
					(hasDevice() ? "Device Discovered" : "No Device Connected") 
					+ ", Midi Input: " + (Config.MIDI_INPUT_ENABLED.get() ? "Enabled" : "Disabled")), x, y);
		this.func_230459_a_(stack, x, y);
	}
	
	private boolean hasDevice() {
		if(BuildersAddition.midi != null)
			return BuildersAddition.midi.hasDevice();
		return false;
	}

	public TileEntity getTE() {
		return te;
	}

	private void sendNote(int note) {
		if (getTE() != null)
			BuildersAddition.Network.sendToServer(new PlayNotePacket(this.getTE().getPos(), note));
	}

	@Override
	public void minecraftNote(int note, boolean on) {
		note += 24;
		if (note < 0)
			return;
		if (on)
			sendNote(note);
	}

	// drawGuiContainerBackgroundLayer
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		renderBackground(stack);//renderBackground

		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
}