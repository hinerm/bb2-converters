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

import java.util.HashMap;
import java.util.Map;

import net.bb2.JSONtoWikiConverter;
import net.bb2.WikiUtils;

import org.json.JSONObject;
import org.scijava.plugin.Plugin;

@Plugin(type = JSONtoWikiConverter.class)
public class CommanderConverter extends XLSWikiConverter {

	private final String COMMANDERS = "commanders";
	private final String COMMANDER_TABLE = "commander_table";
	private final String COMMANDER_PAGES = "commander_pages";
	private final String COMMANDERS_HEADER =
		"Commander ID\tCommander Full Name\tRarity\tAffinity\tRace\tActive Skill\tLeader Skill\tSkill Source\tCharge\tCapturable in Story\tMin HP\tMin ATK\tMin DEF\tMin WIS\tMax HP\tMax ATK\tMax DEF\tMax WIS\tEvolution Step\tEvolves Into (Id)\tMax Ascendency\t0/x Asc Max Level\tMax Level\tBig Soul Id\tSoul Id\tDismiss Gold\tBase Friendliness\tFriendliness Growth\tMax Friendliness\tBio\tNew Message\tFail Message\tEvo Message\tAwakening\tTroop Asset Id\t0 Asc Max HP\t0 Asc Max ATK\t0 Asc Max DEF\t0 Asc Max WIS";
	private final String COMMANDER_TABLE_HEADER = "Code for Commander Table";
	private final String COMMANDER_PAGES_HEADER = "Code for Commander Page";

	@Override
	public String[] getURLs() {
		return new String[] { "Characters.json", "new/Characters.json" };
	}

	@Override
	public Map<String, String> getColumnHeaders() {
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(COMMANDERS, COMMANDERS_HEADER);
		headers.put(COMMANDER_TABLE, COMMANDER_TABLE_HEADER);
		headers.put(COMMANDER_PAGES, COMMANDER_PAGES_HEADER);
		return headers;
	}

	@Override
	public Map<String, String> convert(final JSONObject json) {
		final Map<String, String> lines = new HashMap<String, String>();
		parse(json, lines);
		return lines;
	}

	private void parse(final JSONObject json, final Map<String, String> lines) {

		// --- parse commander info ---
		final String id = getField(json, "Id");
		final String name = getField(json, "Name", "SecondaryTitle");
		final String rarity = getField(json, "Rarity");
		final String affinity =
			WikiUtils.getAffinity(json.get("Affinity").toString());
		final String race = WikiUtils.getRace(json.get("Race").toString());
		final String activeSkill = getField(json, "ActiveSkillName");
		final String leaderSkill = getField(json, "LeaderSkillName");
		final String skillStat =
			WikiUtils.getSkillStat(json.get("SkillStat").toString());
		final String skillSpeed = getField(json, "SkillSpeed");

		String captures;
		if (!json.has("captures") || json.getJSONArray("captures").length() == 0) captures =
			"No";
		else captures = "Yes";

		final String minHp = getField(json, "MinHP");
		final String minAtk = getField(json, "MinAttack");
		final String minDef = getField(json, "MinDefense");
		final String minWis = getField(json, "MinWisdom");
		final String maxHp = getField(json, "MaxHP");
		final String maxAtk = getField(json, "MaxAttack");
		final String maxDef = getField(json, "MaxDefense");
		final String maxWis = getField(json, "MaxWisdom");
		final String evo = getField(json, "Evo");

		String evoInto;
		if (json.get("EvolvesInto").toString().equals("0")) evoInto = " ";
		else evoInto = getField(json, "EvolvesInto");

		final String maxAsc = getField(json, "MaxAsc");
		final String minMaxLvl = getField(json, "MinMaxLevel");
		final String maxMaxLvl = getField(json, "MaxMaxLevel");
		final String dupSoul = getField(json, "DupSoulId");
		final String evoSoul = getField(json, "EvolveSoulId");
		final String dismiss = getField(json, "DismissGold");
		final String baseFriend = getField(json, "BaseFriendliness");
		final String friendGrowth = getField(json, "FriendlinessGrowth");
		final String maxFriend = getField(json, "MaxFriendliness");
		final String desc = getField(json, "Description");
		final String msgNew = getField(json, "MessageNew");
		final String msgFail = getField(json, "MessageFail");
		final String msgEvo = getField(json, "MessageEvo");
		final String awaken = getField(json, "Awakening");
		final String troopAsset = getField(json, "TroopAssetId");
		final String zeroMaxHp = zeroMax(minHp, maxHp, minMaxLvl, maxMaxLvl);
		final String zeroMaxAtk = zeroMax(minAtk, maxAtk, minMaxLvl, maxMaxLvl);
		final String zeroMaxDef = zeroMax(minDef, maxDef, minMaxLvl, maxMaxLvl);
		final String zeroMaxWis = zeroMax(minWis, maxWis, minMaxLvl, maxMaxLvl);


		// --- Build commander database ---
		StringBuilder sb = new StringBuilder();

		append(sb, id);
		append(sb, name);
		append(sb, rarity);
		append(sb, affinity);
		append(sb, race);
		append(sb, activeSkill);
		append(sb, leaderSkill);
		append(sb, skillStat);
		append(sb, skillSpeed);
		append(sb, captures);
		append(sb, minHp);
		append(sb, minAtk);
		append(sb, minDef);
		append(sb, minWis);
		append(sb, maxHp);
		append(sb, maxAtk);
		append(sb, maxDef);
		append(sb, maxWis);
		append(sb, evo);
		append(sb, evoInto);
		append(sb, maxAsc);
		append(sb, minMaxLvl);
		append(sb, maxMaxLvl);
		append(sb, dupSoul);
		append(sb, evoSoul);
		append(sb, dismiss);
		append(sb, baseFriend);
		append(sb, friendGrowth);
		append(sb, maxFriend);
		append(sb, desc);
		append(sb, msgNew);
		append(sb, msgFail);
		append(sb, msgEvo);
		append(sb, awaken);
		append(sb, troopAsset);

		append(sb, zeroMaxHp);
		append(sb, zeroMaxAtk);
		append(sb, zeroMaxDef);
		append(sb, zeroMaxWis);

		lines.put(COMMANDERS, sb.toString());

		// --- Build commanders table ---

		sb = new StringBuilder();

		sb.append("{{CommTableRow|" + name + "|" + rarity + "|" + affinity + "|" +
			race + "|" +
			(leaderSkill.equals(" ") ? "" : "lskill=" + leaderSkill + "|") +
			activeSkill + "|" + skillStat + "|" + skillSpeed + "|" + captures + "|" +
			maxHp + "|" + maxAtk + "|" + maxDef + "|" + maxWis + "}}");
		lines.put(COMMANDER_TABLE, sb.toString());

		// --- Build commanders pages ---

		sb = new StringBuilder();

		sb.append("{{Commander " +
			"| name =  " + name + " " +
			"| image = " +
			"| race = " + race + " " +
			"| maxlvl = " + maxMaxLvl + " " +
			"| rarity = " + rarity + " " +
			"| leaderskill = " + leaderSkill + " " +
			"| skill = " + activeSkill + " " +
			"| skillbased = " + skillStat + " " +
			"| charge = " + skillSpeed + " " +
			"| affinity = " + affinity + " " +
			"| bio = " + desc + " " +
			"| basehp = " + minHp + " " +
			"| baseatk = " + minAtk + " " +
			"| basedef = " + minDef + " " +
			"| basewis = " + minWis + " " +
			"| 0ascmaxhp = " + zeroMaxHp + " " +
			"| 0ascmaxatk = " + zeroMaxAtk + " " +
			"| 0ascmaxdef = " + zeroMaxDef + " " +
			"| 0ascmaxwis = " + zeroMaxWis + " " +
			"| basemaxhp = " + maxHp + " " +
			"| basemaxatk = " + maxAtk + " " +
			"| basemaxdef = " + maxDef + " " +
			"| basemaxwis = " + maxWis + " " +
			"| soulhp = " +
			"| soulatk = " +
			"| souldef = " +
			"| soulwis = " +
			"| loc1 =  " +
			"| loc2 =  " +
			"| loc3 =  " +
			"| loc4 =  " +
			"| loc5 =  " +
			"| evostep1 = " +
			"| evostep2 =  " +
			"| lskilltext =  " +
			"| askilltext = }}");

		lines.put(COMMANDER_PAGES, sb.toString());
	}

	private String zeroMax(final String minStat, final String maxStat,
		final String zeroAscLvl, final String maxLvl)
	{
		return String.valueOf((int) Math.floor(Double.valueOf(minStat) +
			(Double.valueOf(maxStat) - Double.valueOf(minStat)) /
			(Double.valueOf(maxLvl) - 1) * (Double.valueOf(zeroAscLvl) - 1)));
	}

}
