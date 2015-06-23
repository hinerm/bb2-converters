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

//@Plugin(type = JSONtoWikiConverter.class)
public class SkillConverter extends XLSWikiConverter {

	private final String SKILL_PAGES = "skill_page";
	private final String SKILL_PAGES_HEADER = "Skill name\tCode for Skill Pages";

	@Override
	public String[] getURLs() {
		return new String[] { "ActiveSkill.json", "new/ActiveSkill.json" };
	}

	@Override
	public Map<String, String> getColumnHeaders() {
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(SKILL_PAGES, SKILL_PAGES_HEADER);
		return headers;
	}

	@Override
	public Map<String, String> convert(JSONObject json) {
		final Map<String, String> lines = new HashMap<String, String>();

		StringBuilder sb = new StringBuilder();

		// --- Build Skill Pages ---
		append(sb, "Category:" + getField(json, "Name"));
		sb.append("{{Skills " +
				"|skillid = " + getField(json, "Id") + " " +
				"|skilltext = " + getField(json, "Desc") + " ");

		// FIXME: not clear how to translate these to skills
		// BuffId > Battle buff > Poison, damage, etc..

		for (int i=0; i<json.getJSONArray("SkillType").length(); i++) {
			
		
//		sb.append(
//				"|skilltype1 = Indirect " + (i + 1) + " = " + WikiUtils.getSkillType(json.getJSONArray("SkillType").get(i).toString()) + " " +
//				"|indirecttype" + (i + 1) + " = " + WikiUtils.getSkillType(json.getJSONArray("BuffIds").get(i).toString()) + " " +
//				"|modifier1 = 25 " + (i + 1) + " = " + getVal(json.getJSONArray("Magnitude").get(i).toString()) + " " +
//				"|probability" + (i + 1) + " = " + json.getJSONArray("Prob").get(i).toString() + " " +
//				"|atkPattern " + (i + 1) + " = " + //TODO
//				"|target11 = 1 Random " + (i + 1) + //TODO
//				"|target" + (i + 1) + "2 = " + WikiUtils.getTargetType(json.getJSONArray("TargetType").get(i).toString()) + " " +
//				"|triggers" + (i + 1) + " = " + getField(json, "NumTriggers") + " " +
//				"|indirecteffect1 = WIS Down " + (i + 1) + " = " + // TODO if indirect
//				"|effectDuration" + (i + 1) + " = " + json.getJSONArray("Duration").get(i).toString() + " " +
//				"|affinityAffected1 =  " + (i + 1) + " = "
//				);
		}

		sb.append("}}");

		lines.put(SKILL_PAGES, sb.toString());

		return lines;
	}

	private String getVal(final String s) {
		final Double i = Double.parseDouble(s);
		return String.valueOf((int) Math.abs(i));
	}

}
