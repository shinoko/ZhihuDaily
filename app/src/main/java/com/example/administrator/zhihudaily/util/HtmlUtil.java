package com.example.administrator.zhihudaily.util;

import java.util.List;

/**
 * Created by shinoko on 2016/8/9.
 */
public class HtmlUtil {

    //css样式,隐藏header
    private static final String HIDE_HEADER_STYLE = "<style>div.headline{display:none;}</style>";
    //css style tag,需要格式化
    private static final String NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";
    // js script tag,需要格式化
    private static final String NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>";

    /**
     * 根据css链接生成Link标签
     *
     * @param url String
     * @return String
     */
    public static String createCssTag(String url) {
        return String.format(NEEDED_FORMAT_CSS_TAG, url);
    }

    /**
     * 根据多个css链接生成Link标签
     *
     * @param urls List<String>
     * @return String
     */
    public static String createCssTag(List<String> urls) {
        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createCssTag(url));
        }
        return sb.toString();
    }

    /**
     * 根据js链接生成Script标签
     *
     * @param url String
     * @return String
     */
    public static String createJsTag(String url) {
        return String.format(NEEDED_FORMAT_JS_TAG, url);
    }

    /**
     * 根据多个js链接生成Script标签
     *
     * @param urls List<String>
     * @return String
     */
    public static String createJsTag(List<String> urls) {
        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createJsTag(url));
        }
        return sb.toString();
    }



    public static StringBuffer handleHtml(String body, List<String> css, List<String> js) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head>");

        if(css != null){
            stringBuffer.append(createCssTag(css));
        }
        if(js != null){
            stringBuffer.append(createJsTag(js));
        }

        stringBuffer.append(HIDE_HEADER_STYLE);
        stringBuffer.append("</head><body>");
        stringBuffer.append(body);
        stringBuffer.append("</body></html>");
        return stringBuffer;
    }




}
