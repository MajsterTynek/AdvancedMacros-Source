package com.theincgi.advancedMacros.lua.functions;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.TempCategory;

public class GetBiome extends VarArgFunction{
	@Override
	public Varargs invoke(Varargs args) {
		BlockPos pos = null;
		
		if(args.narg()==0) {
			pos = Minecraft.getMinecraft().player.getPosition();
		}else if(args.narg()==2){
			pos = new BlockPos(args.arg(1).checkint(),64, args.arg(2).checkint());
		}else {
			throw new LuaError("Invalid args: NONE or X, Z");
		}
		World w = Minecraft.getMinecraft().player.getEntityWorld();
		LuaTable out = new LuaTable();
		Biome b = w.getBiomeForCoordsBody(pos);
		out.set(1, b.getBiomeName());
		LuaTable details = new LuaTable();
		details.set("canSnow", LuaValue.valueOf(b.getEnableSnow()));
		details.set("canRain", LuaValue.valueOf(b.canRain()));
		details.set("rainfall", b.getRainfall());
		details.set("temp", b.getTempCategory().name().toLowerCase()); //cold medium warm ocean
		out.set(2, details);
		return out.unpack();
	}
}