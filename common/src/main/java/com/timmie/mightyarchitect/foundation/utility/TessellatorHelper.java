package com.timmie.mightyarchitect.foundation.utility;//package com.timmie.mightyarchitect.foundation.utility;
//
//import org.lwjgl.opengl.GL11;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.ActiveRenderInfo;
//import net.minecraft.client.renderer.BufferBuilder;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Vector3d;
//
//public class TessellatorHelper {
//
//    public static final float fontScale = 1/512f;
//
//    public static void prepareForDrawing() {
//        Minecraft mc = Minecraft.getInstance();
//        RenderSystem.pushMatrix();
//        RenderSystem.pushLightingAttributes();
//        RenderSystem.enableBlend();
//        RenderSystem.enableAlphaTest();
//        RenderSystem.color4f(1, 1, 1, 1);
//
//        ActiveRenderInfo renderInfo = mc.gameRenderer.getActiveRenderInfo();
//        Vector3d view = renderInfo.getProjectedView();
//        RenderSystem.translated(-view.x, -view.y, -view.z);
//    }
//
//    public static void begin() {
//        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
//    }
//
//    public static void draw() {
//        Tessellator.getInstance().draw();
//    }
//
//    public static void cleanUpAfterDrawing() {
//        RenderSystem.disableAlphaTest();
//        RenderSystem.disableBlend();
//        RenderSystem.popAttributes();
//        RenderSystem.popMatrix();
//    }
//
//	public static void drawString(String str, float x, float y, float z, boolean scalesUp, boolean hasDepth) {
//		Minecraft mc = Minecraft.getInstance();
//		float pitch = mc.getRenderManager().playerViewX;
//		float yaw = mc.getRenderManager().playerViewY;
//		boolean isThirdPersonFrontal = mc.gameSettings.thirdPersonView == 2;
//		
//		RenderSystem.pushMatrix();
//		RenderSystem.pushLightingAttributes();
//		RenderSystem.translatef(x, y, z);
//		RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
//		RenderSystem.rotatef(-yaw, 0.0F, 1.0F, 0.0F);
//		RenderSystem.rotatef((float) (isThirdPersonFrontal ? -1 : 1) * pitch, 1.0F, 0.0F, 0.0F);
//		RenderSystem.scalef(-0.025F, -0.025F, 0.025F);
//		RenderSystem.disableLighting();
//		if (!hasDepth) {
//			RenderSystem.depthMask(false);
//			RenderSystem.disableDepthTest();			
//		}
//	
//		RenderSystem.enableBlend();
//		RenderSystem.blendFuncSeparate(RenderSystem.SourceFactor.SRC_ALPHA,
//				RenderSystem.DestFactor.ONE_MINUS_SRC_ALPHA, RenderSystem.SourceFactor.ONE,
//				RenderSystem.DestFactor.ZERO);
//		int i = mc.fontRenderer.getStringWidth(str) / 2;
//		RenderSystem.disableTexture();
//		Tessellator tessellator = Tessellator.getInstance();
//		BufferBuilder bufferbuilder = tessellator.getBuffer();
//		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
//		bufferbuilder.pos((double) (-i - 3), (double) (-3), 0.0D).color(1F, 1F, 1F, .5F).endVertex();
//		bufferbuilder.pos((double) (-i - 3), (double) (10), 0.0D).color(1F, 1F, 1F, .5F).endVertex();
//		bufferbuilder.pos((double) (i + 3), (double) (10), 0.0D).color(1F, 1F, 1F, .5F).endVertex();
//		bufferbuilder.pos((double) (i + 3), (double) (-3), 0.0D).color(1F, 1F, 1F, .5F).endVertex();
//	
//		if (scalesUp) {
//			double distance = mc.player.getEyePosition(mc.getRenderPartialTicks()).squareDistanceTo(x, y, z);
//			double scale = distance * fontScale;
//			RenderSystem.scaled(2 + scale, 2 + scale, 2 + scale);			
//		}
//		tessellator.draw();
//		RenderSystem.enableTexture();
//		if (hasDepth) {
//			RenderSystem.translatef(0, 0, -0.125f);
//		}
//	
//		mc.fontRenderer.drawString(str, -mc.fontRenderer.getStringWidth(str) / 2, 0, 0);
//		RenderSystem.enableDepthTest();
//	
//		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//		RenderSystem.depthMask(true);
//		RenderSystem.blendFuncSeparate(RenderSystem.SourceFactor.SRC_ALPHA,
//				RenderSystem.DestFactor.ONE_MINUS_SRC_ALPHA, RenderSystem.SourceFactor.SRC_ALPHA,
//				RenderSystem.DestFactor.ONE_MINUS_SRC_ALPHA);
//		RenderSystem.popMatrix();
//		RenderSystem.popAttributes();
//	}
//    
//    public static void cube(BufferBuilder bufferBuilder, BlockPos pos, BlockPos size, double scale,
//                            boolean scaleVertical, boolean doubleFaces) {
//        TessellatorHelper.walls(bufferBuilder, pos, size, scale, scaleVertical, doubleFaces);
//        int w = size.getX();
//        int h = size.getY();
//        int l = size.getZ();
//
//        if (doubleFaces) {
//            TessellatorHelper.doubleFace(bufferBuilder, pos, new BlockPos(w, 0, l), scale, true, scaleVertical, false);
//            TessellatorHelper.doubleFace(bufferBuilder, pos.east(w).up(h), new BlockPos(-w, 0, l), scale, true, scaleVertical, false);
//        } else {
//            TessellatorHelper.face(bufferBuilder, pos, new BlockPos(w, 0, l), scale, true, scaleVertical, false, false);
//            TessellatorHelper.face(bufferBuilder, pos.east(w).up(h), new BlockPos(-w, 0, l), scale, true, scaleVertical, false, false);
//        }
//    }
//
//    public static void walls(BufferBuilder bufferBuilder, BlockPos pos, BlockPos size, double scale,
//                             boolean scaleVertical, boolean doubleFaces) {
//        int w = size.getX();
//        int h = size.getY();
//        int l = size.getZ();
//
//        if (doubleFaces) {
//            TessellatorHelper.doubleFace(bufferBuilder, pos, new BlockPos(w, h, 0), scale, true, scaleVertical, false);
//            TessellatorHelper.doubleFace(bufferBuilder, pos.east(w).south(l), new BlockPos(0, h, -l), scale, true, scaleVertical, false);
//            TessellatorHelper.doubleFace(bufferBuilder, pos.east(w).south(l), new BlockPos(-w, h, 0), scale, true, scaleVertical, false);
//            TessellatorHelper.doubleFace(bufferBuilder, pos, new BlockPos(0, h, l), scale, true, scaleVertical, false);
//        } else {
//            TessellatorHelper.face(bufferBuilder, pos, new BlockPos(w, h, 0), scale, true, scaleVertical, false, false);
//            TessellatorHelper.face(bufferBuilder, pos.east(w).south(l), new BlockPos(0, h, -l), scale, true, scaleVertical, false, false);
//            TessellatorHelper.face(bufferBuilder, pos.east(w).south(l), new BlockPos(-w, h, 0), scale, true, scaleVertical, false, false);
//            TessellatorHelper.face(bufferBuilder, pos, new BlockPos(0, h, l), scale, true, scaleVertical, false, false);
//        }
//    }
//
//    public static void doubleFace(BufferBuilder bufferBuilder, BlockPos pos, BlockPos size, double shift,
//                                  boolean stretch, boolean shiftVertical, boolean mirrorTexture) {
//        TessellatorHelper.face(bufferBuilder, pos, size, shift, stretch, shiftVertical, false, mirrorTexture);
//        TessellatorHelper.face(bufferBuilder, pos.add(size.getX(), 0, (size.getY() == 0) ? 0 : size.getZ()),
//                new BlockPos(-size.getX(), size.getY(), (size.getY() == 0) ? size.getZ() : -size.getZ()), -shift,
//                stretch, shiftVertical, true, mirrorTexture);
//    }
//
//    public static void face(BufferBuilder bufferBuilder, BlockPos pos, BlockPos size, double shift, boolean stretch,
//                            boolean shiftVertical, boolean shiftBackwards, boolean mirrorTexture) {
//        int w = size.getX();
//        int h = size.getY();
//        int l = size.getZ();
//        if (shiftBackwards)
//            shift = -shift;
//
//        if (w == 0) { // YZ plane -> H has to be positive
//
//            double xs = (l < 0) ? shift : -shift;
//            if (shiftBackwards)
//                xs = -xs;
//            double ys1 = shiftVertical ? shift : 0;
//            double zs1 = l < 0 ? -shift : shift;
//            if (!stretch && (l > 0 ^ mirrorTexture))
//                zs1 = -zs1;
//            double ys2 = stretch ? -ys1 : ys1;
//            double zs2 = stretch ? -zs1 : zs1;
//            double u1 = (mirrorTexture) ? l : 0;
//            double u2 = (mirrorTexture) ? 0 : l;
//
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs, ys2, zs1), pos.south(l), u2, h);
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs, ys1, zs1), pos.south(l).up(h), u2, 0);
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs, ys1, zs2), pos.up(h), u1, 0);
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs, ys2, zs2), pos, u1, h);
//
//        } else if (h == 0) { // XZ plane -> L has to be positive
//
//            double ys = w < 0 ? shift : -shift;
//            if (shiftBackwards)
//                ys = -ys;
//            double xs1 = w < 0 ? -shift : shift;
//            double zs1 = shift;
//            double xs2 = stretch ? -xs1 : xs1;
//            double zs2 = stretch ? -zs1 : zs1;
//            double u1 = (mirrorTexture) ? w : 0;
//            double u2 = (mirrorTexture) ? 0 : w;
//
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs2, ys, zs1), pos.south(l), u1, l);
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs2, ys, zs2), pos, u1, 0);
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs1, ys, zs2), pos.east(w), u2, 0);
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs1, ys, zs1), pos.east(w).south(l), u2, l);
//
//        } else if (l == 0) { // XY plane -> H has to be positive
//
//            double zs = w < 0 ? shift : -shift;
//            if (shiftBackwards)
//                zs = -zs;
//            double ys1 = shiftVertical ? shift : 0;
//            double xs1 = w < 0 ? -shift : shift;
//            if (!stretch && (w > 0 ^ mirrorTexture))
//                xs1 = -xs1;
//            double ys2 = stretch ? -ys1 : ys1;
//            double xs2 = stretch ? -xs1 : xs1;
//            double u1 = (mirrorTexture) ? w : 0;
//            double u2 = (mirrorTexture) ? 0 : w;
//
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs2, ys2, zs), pos, u1, h);
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs2, ys1, zs), pos.up(h), u1, 0);
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs1, ys1, zs), pos.east(w).up(h), u2, 0);
//            TessellatorHelper.posTexShift(bufferBuilder, Vector3d.of(xs1, ys2, zs), pos.east(w), u2, h);
//
//        }
//    }
//
//    private static void posTexShift(BufferBuilder bufferBuilder, Vector3d shift, BlockPos pos, double u, double v) {
//        bufferBuilder.pos(shift.x + pos.getX(), shift.y + pos.getY(), shift.z + pos.getZ()).tex(u, v).endVertex();
//    }
//
//}
