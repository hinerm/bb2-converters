
package net.bb2.converter;

import java.util.HashMap;
import java.util.Map;

import net.bb2.AbstractJSONtoWikiConverter;
import net.bb2.JSONtoWikiConverter;

import org.json.JSONObject;
import org.scijava.plugin.Plugin;

@Plugin(type = JSONtoWikiConverter.class)
public class GearConverter extends AbstractJSONtoWikiConverter {

	private final String GEAR = "gear";

	@Override
	public String[] getURLs() {
		return new String[] { "http://www.bloodbrothers2.info/3816/Gear.json",
			"http://www.bloodbrothers2.info/3816/new/Gear.json" };
	}

	@Override
	public Map<String, String> getColumnHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(GEAR, "Gear ID\tGear Name\tRarity\tSell Value\tHP\tATK\tDEF\tWIS");
		return headers;
	}

	@Override
	public Map<String, String> convert(JSONObject json) {
		return new HashMap<String, String>();
	}

}
