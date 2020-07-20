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
	public void func_231158_b_(Minecraft p_231158_1_, int p_231158_2_, int p_231158_3_) {
		super.func_231158_b_(p_231158_1_, p_231158_2_, p_231158_3_);
		int x = this.field_230708_k_ / 2;// with
		int y = this.field_230709_l_ / 2;// height

		IPressable p = (b) -> {
		};

		connectBtn = new Button(x - 48, y + 24 * 4, 96, 20,
				new TranslationTextComponent(BuildersAddition.midi == null ? "container.buildersaddition.speaker.connect" : "container.buildersaddition.speaker.disconnect"), (b) -> {
					if (BuildersAddition.midi != null) {
						if (BuildersAddition.midi.midiEvent == null)
							BuildersAddition.midi.midiEvent = this;
						else
							BuildersAddition.midi.midiEvent = null;

						connectBtn.func_238482_a_(new TranslationTextComponent(
								BuildersAddition.midi.midiEvent == null ? "container.buildersaddition.speaker.connect" : "container.buildersaddition.speaker.disconnect"));// SetMessage
					}
				});

		this.func_230480_a_(connectBtn);// addButton

		btns = new Button[SIZE];

		for (int i = 0; i < SIZE; i++) {
			btns[i] = new Button(x + (i > 7 ? -100 : 4), y + (i % 8 * 24) - 4 * 24, 96, 20,
					new TranslationTextComponent("note.buildersaddition." + Notes.instrumentNames[i]), p);
			this.func_230480_a_(btns[i]);// addButton
			btns[i].field_230693_o_ = te.isInstrumentActive(i);// active
		}
	}

	// mouseClicked
	@Override
	public boolean func_231044_a_(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		for (int i = 0; i < SIZE; i++) {
			if (btns[i].func_230449_g_())// isHovered
				buttonClicked(btns[i], i);
		}

		return super.func_231044_a_(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}

	private void buttonClicked(Button b, int id) {
		b.field_230693_o_ = !b.field_230693_o_;// active
		sendInstrumentUpdate(getEncoded());
	}

	private int getEncoded() {
		int r = 0;
		for (int i = 0; i < SIZE; i++) {
			r += btns[i].field_230693_o_ ? Math.pow(2, i) : 0;
		}
		return r;
	}

	private void sendInstrumentUpdate(int data) {
		if (getTE() != null)
			BuildersAddition.Network.sendToServer(new UpdateDataPacket(this.getTE().getPos(), data));
	}

	// render
	@Override
	public void func_230430_a_(MatrixStack stack, int x, int y, float p_230430_4_) {
		super.func_230430_a_(stack, x, y, p_230430_4_);
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();

		for (int i = 0; i < SIZE; i++) {
			if (btns[i].func_230449_g_())// isHovered
				func_238652_a_(stack, new StringTextComponent("F#" + Notes.octaveNames[i]), x, y);
		}
		this.func_230459_a_(stack, x, y);
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
	protected void func_230450_a_(MatrixStack stack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		func_230446_a_(stack);//renderBackground

		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
}