package lab.tgna.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_main{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("btnkata").vw.setTop((int)((55d / 100 * height) - (views.get("btnkata").vw.getHeight())));
//BA.debugLineNum = 4;BA.debugLine="btnKata.Left=15%y"[main/General script]
views.get("btnkata").vw.setLeft((int)((15d / 100 * height)));
//BA.debugLineNum = 6;BA.debugLine="btnVideo.Bottom=70%y"[main/General script]
views.get("btnvideo").vw.setTop((int)((70d / 100 * height) - (views.get("btnvideo").vw.getHeight())));
//BA.debugLineNum = 7;BA.debugLine="btnVideo.Left=15%y"[main/General script]
views.get("btnvideo").vw.setLeft((int)((15d / 100 * height)));
//BA.debugLineNum = 9;BA.debugLine="btnBiodata.Bottom=40%y"[main/General script]
views.get("btnbiodata").vw.setTop((int)((40d / 100 * height) - (views.get("btnbiodata").vw.getHeight())));
//BA.debugLineNum = 10;BA.debugLine="btnBiodata.Left=15%y"[main/General script]
views.get("btnbiodata").vw.setLeft((int)((15d / 100 * height)));

}
}