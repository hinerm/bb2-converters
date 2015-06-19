package net.bb2;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentListEntry;

import java.io.File;
import java.net.URL;

import net.bb2.converter.XLSWikiConverter;


public class GoogleUploader {

	public static void upload() throws Exception {

		DocsService docsService = new DocsService("MySampleApplication-v3");
    URL GOOGLE_DRIVE_FEED_URL = new URL("https://docs.google.com/feeds/default/private/full/");
		DocumentListEntry uploadFile = new DocumentListEntry();
		uploadFile.setTitle(new PlainTextConstruct("BloodBrothers_2_data"));
		uploadFile.setFile(new File(XLSWikiConverter.OUTPUT), "application/vnd.ms-excel");
		uploadFile = docsService.insert(GOOGLE_DRIVE_FEED_URL, uploadFile);
	}

}
