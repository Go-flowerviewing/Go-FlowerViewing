package com.HWHH.henry.goflower_viewing;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Henry on 2015. 3. 25..
 */
public class DAO {

    private MusicArrayList playList;
    private Context context;

    public DAO(Context context) {
        playList = new MusicArrayList();
    }

    public void getPlayList(final String version, final OnResponseListener onResponseListener) {

        new GetPlayList().execute(version, new OnResponseListener() {

            @Override
            public void onResponse(Response response) {

                Response playListRes = new Response();

                try {

                    String xmlData = (String) response.get("playList");
                    InputSource is = new InputSource(new StringReader(xmlData));
                    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
                    doc.getDocumentElement().normalize();

                    NodeList musicList = doc.getElementsByTagName("Music");

                    for (int i = 0; i < musicList.getLength(); i++) {

                        Node musicNode = musicList.item(i);

                        if (musicNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element musicElement = (Element) musicNode;
                            NodeList musicIdNodeList = musicElement.getElementsByTagName("id");
                            Element musicIdElement = (Element) musicIdNodeList.item(0);
                            Node musicIdNode = musicIdElement.getFirstChild();
                            String musicId = musicIdNode.getNodeValue();

                            NodeList musicTitleNodeList = musicElement.getElementsByTagName("title");
                            Element musicTitleElement = (Element) musicTitleNodeList.item(0);
                            Node musicTitleNode = musicTitleElement.getFirstChild();
                            String musicTitle = musicTitleNode.getNodeValue();
                            playList.add(new Music(musicId, musicTitle));

                        }

                    }

                } catch (Exception e) {

                    Log.e("test", "Exception : " + e);

                }

                playListRes.add("playList", playList);
                onResponseListener.onResponse(playListRes);

            }

        });

    }

}
