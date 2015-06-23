/*
 * #%L
 * Blood Brothers 2 Converters.
 * %%
 * Copyright (C) 2015 Blood Brothers 2 Wiki Members
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.bb2.converter;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import net.bb2.JSONtoWikiConverter;
import net.bb2.WikiUtils;

import org.json.JSONObject;
import org.scijava.plugin.Plugin;

@Plugin(type = JSONtoWikiConverter.class)
public class SoulsConverter extends XLSWikiConverter {

	private final String SOULS = "souls";
	private final String SOUL_TABLE = "soul_table";
	private final String SOUL_PAGES = "soul_pages";

	@Override
	public String[] getURLs() {
		return new String[] { "Souls.json", "new/Souls.json" };
	}

	@Override
	public Map<String, String> getColumnHeaders() {
		final Map<String, String> headers = new HashMap<String, String>();
		headers
			.put(
				SOULS,
				"Soul ID\tSoul Name\tRarity\tSell Value\tHP\tATK\tDEF\tWIS\tTotal\tAverage\tBalance");
		headers.put(SOUL_TABLE, "Code for Soul Table");
		headers.put(SOUL_PAGES, "Code for Soul Pages");
		return headers;
	}

	@Override
	public Map<String, String> convert(final JSONObject json) {
		final Map<String, String> lines = new HashMap<String, String>();

		// Parse soul info
		final String id = getField(json, "Id");
		if (id.endsWith("2")) {
			final String name = getField(json, "Name");
			final String rarity = getField(json, "Rarity");
			final String value = getField(json, "SellValue");
			final String hp = getField(json, "Hp");
			final String atk = getField(json, "Attack");
			final String def = getField(json, "Defense");
			final String wis = getField(json, "Wisdom");
			final String race = WikiUtils.getRace(getField(json, "Race").toString());
			final String affinity =
				WikiUtils.getAffinity(getField(json, "Affinity").toString());

			// --- Build Souls Database ---
			StringBuilder sb = new StringBuilder();

			// Only output souls created after the removal of pink/blue souls
			append(sb, id);
			append(sb, name);
			append(sb, rarity);
			append(sb, value);
			append(sb, hp);
			append(sb, atk);
			append(sb, def);
			append(sb, wis);
			final String[] stats = computeStats(hp, atk, def, wis);
			append(sb, stats[0]);
			append(sb, stats[1]);
			append(sb, stats[2]);

			lines.put(SOULS, sb.toString());

			// --- Build Wiki Soul Table code ---
			sb = new StringBuilder();
			// {{SoulTableRow|Andarion's Soul
			// (L)|4|Cavalry|Darklander|342|132|334|99|907}}
			sb.append("{{SoulTableRow|" + name + "|" + rarity + "|" + affinity + "|" +
				race + "|" + hp + "|" + atk + "|" + def + "|" + wis + "|}}");
			lines.put(SOUL_TABLE, sb.toString());

			// -- Build Soul Page code --
			sb = new StringBuilder();

			sb.append("<div style=\"float:left;\">{{Souls" +
					"| name = " + name +
					"| image =" +
					"|caption = Achieved from " + (name.contains("(L)") ? "absolving" : "evolving") + " [[ ]]" + // FIXME put in page link
					"|HP = " + hp +
					"|DEF = " + def +
					"|ATK = " + atk +
					"|WIS = " + wis +
					"|rarity = " + rarity +
					"|affinity = " + affinity +
					"|race = " + race +
					"}}</div>");
			lines.put(SOUL_PAGES, sb.toString());
		}
		return lines;
	}

	/**
	 * @return Array containing the computed Total, Average, and Balance
	 */
	private String[] computeStats(final String hpStr, final String atkStr,
		final String defStr, final String wisStr)
	{
		final int str = Integer.parseInt(hpStr);
		final int atk = Integer.parseInt(atkStr);
		final int def = Integer.parseInt(defStr);
		final int wis = Integer.parseInt(wisStr);
		final int total = str + atk + def + wis;
		final int avg = total / 4;
		final double balance =
			(Math.abs(str - avg) + 1.8 * Math.abs(atk - avg) + 1.8 *
				Math.abs(def - avg) + 1.7 * Math.abs(wis - avg)) /
				avg;

		final String[] stats = new String[3];
		stats[0] = String.valueOf(total);
		stats[1] = String.valueOf(avg);
		final DecimalFormat df = new DecimalFormat("#.00");
		stats[2] = String.valueOf(df.format(balance));

		return stats;
	}
}
