package net.srivastav;

import org.newdawn.slick.*;
import org.newdawn.slick.util.*;
import org.newdawn.slick.font.effects.ColorEffect;
import java.awt.Font;

public class Fonts 
{
	@SuppressWarnings("unchecked")
	public static UnicodeFont getRetroFont(java.awt.Color color, float size)
	{
		Font UIFont = null;
		UnicodeFont uniFont = null;
		try
		{
			UIFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("assets/8-BIT Wonder.TTF"));
			UIFont = UIFont.deriveFont(Font.PLAIN, size);
			uniFont = new UnicodeFont(UIFont);
			uniFont.addAsciiGlyphs();
			uniFont.getEffects().add(new ColorEffect(color));
			uniFont.loadGlyphs();
		} catch (Exception e)
		{
			System.out.println(e);
		}
		
		return uniFont;
	}
}
