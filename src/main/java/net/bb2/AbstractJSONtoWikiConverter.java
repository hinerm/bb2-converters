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

package net.bb2;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.StringTokenizer;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.json.JSONObject;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;

public abstract class AbstractJSONtoWikiConverter implements
	JSONtoWikiConverter
{

	public static final String DELIM = "\t";

	@Parameter
	private LogService logService;

	@Override
	public void run() throws MalformedURLException, IOException {
		final File wbLoc = new File(OUTPUT);
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(wbLoc);
		}
		catch (final FileNotFoundException exc) {
			// No problem - workbook didn't exist
		}
		catch (final BiffException exc) {
			logService.error("Problem reading existing XLS file", exc);
		}

		final WritableWorkbook workbook =
			wb == null ? Workbook.createWorkbook(wbLoc) : Workbook.createWorkbook(
				wbLoc, wb);
		try {
			initHeaders(workbook, getColumnHeaders());
		}
		catch (final WriteException exc) {
			throw new IOException(exc);
		}

		int row = 1;

		for (final String url : getURLs()) {
			String page = null;
			try {
				page = Resources.toString(new URL(url), Charsets.UTF_8);
			}
			catch (final FileNotFoundException e) {
				// if URL not found, log and continue
				logService.error("WARNING: URL not found: " + url);
				logService.error(e);
				continue;
			}

			final JSONObject obj = new JSONObject(page);
			for (final String id : JSONObject.getNames(obj)) {
				final Map<String, String> lines = convert(obj.getJSONObject(id));
				try {
					if (lines.keySet().size() > 0) writeXLS(workbook, lines, row++);
				}
				catch (final RowsExceededException exc) {
					throw new IOException(exc);
				}
				catch (final WriteException exc) {
					throw new IOException(exc);
				}
			}
		}

		workbook.write();

		try {
			workbook.close();
		}
		catch (final WriteException exc) {
			throw new IOException(exc);
		}

	}

	protected void append(final StringBuilder sb, final String entry) {
		sb.append(entry);
		sb.append(DELIM);
	}

	protected String getField(final JSONObject json, final String... ids) {
		String s;
		if (ids.length == 0 || !json.has(ids[0]) ||
			json.get(ids[0]).toString().equals("null"))
		{
			s = " ";
		}
		else {
			s = json.get(ids[0]).toString();

			for (int i = 1; i < ids.length; i++) {
				if (!json.get(ids[i]).toString().equals("null")) {
					s += ", " + json.get(ids[i]);
				}
			}
		}
		return s;
	}

	protected String getArrayField(final JSONObject json, final String id,
		int index)
	{
		String s;
		if (!json.has(id) || json.get(id).toString().equals("null")) {
			s = " ";
		}
		else {
			s = json.getJSONArray(id).get(index).toString();
		}
		return s;
	}

	private void initHeaders(final WritableWorkbook workbook,
		final Map<String, String> sheetToRowMap) throws RowsExceededException,
		WriteException
	{
		// Create the sheets
		int sheetNumber = 0;
		for (final String sheet : sheetToRowMap.keySet())
			workbook.createSheet(sheet, sheetNumber++);

		// Write the column headers
		final WritableFont boldFont =
			new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		final WritableCellFormat format = new WritableCellFormat(boldFont);
		format.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		writeXLS(workbook, sheetToRowMap, 0, format);
	}

	private void writeXLS(final WritableWorkbook workbook,
		final Map<String, String> sheetToRowMap, final int row)
		throws RowsExceededException, WriteException
	{
		final WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 10);
		final WritableCellFormat format = new WritableCellFormat(normalFont);
		writeXLS(workbook, sheetToRowMap, row, format);
	}

	private void writeXLS(final WritableWorkbook workbook,
		final Map<String, String> sheetToRowMap, final int row,
		final CellFormat format) throws RowsExceededException, WriteException
	{
		for (final String sheetName : sheetToRowMap.keySet()) {
			final WritableSheet sheet = workbook.getSheet(sheetName);
			final StringTokenizer stk =
				new StringTokenizer(sheetToRowMap.get(sheetName), DELIM);
			int column = 0;
			while (stk.hasMoreTokens()) {
				final Label cell = new Label(column, row, stk.nextToken(), format);
				column++;
				sheet.addCell(cell);
			}
		}

	}
}
