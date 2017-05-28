package lab.tgna.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_intro{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[intro/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 4;BA.debugLine="IRCircularZoom1.Bottom=90%y"[intro/General script]
views.get("ircircularzoom1").vw.setTop((int)((90d / 100 * height) - (views.get("ircircularzoom1").vw.getHeight())));
//BA.debugLineNum = 5;BA.debugLine="IRCircularZoom1.Left=25%y"[intro/General script]
views.get("ircircularzoom1").vw.setLeft((int)((25d / 100 * height)));

}
}