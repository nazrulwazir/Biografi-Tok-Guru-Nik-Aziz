package lab.tgna;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class kata extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static kata mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "lab.tgna", "lab.tgna.kata");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (kata).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "lab.tgna", "lab.tgna.kata");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "lab.tgna.kata", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (kata) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (kata) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return kata.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (kata) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (kata) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        Object[] o;
        if (permissions.length > 0)
            o = new Object[] {permissions[0], grantResults[0] == 0};
        else
            o = new Object[] {"", false};
        processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static int _pagenumber = 0;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _mediaplayer1 = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static String _paragraph = "";
public static String _paragraph2 = "";
public static String _paragraph3 = "";
public static String _paragraph4 = "";
public static String _paragraph5 = "";
public static String _paragraph6 = "";
public static String _paragraph7 = "";
public static String _paragraph8 = "";
public static String _paragraph9 = "";
public static String _paragraph10 = "";
public static String _paragraph11 = "";
public static int _paracount = 0;
public static int _leftmargin = 0;
public static int _topmargin = 0;
public static int _linespacing = 0;
public anywheresoftware.b4a.agraham.pageturnview.CurlViewWrapper _pageturner = null;
public anywheresoftware.b4a.agraham.pageturnview.CurlViewWrapper.TextPaginator _pager = null;
public anywheresoftware.b4a.keywords.constants.TypefaceWrapper _font = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bitmap1 = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _destrect = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _introwel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhome = null;
public de.amberhome.objects.appcompat.AppCompatBase _ac = null;
public de.amberhome.objects.appcompat.ACActionBar _abhelper = null;
public anywheresoftware.b4a.objects.PanelWrapper _pcontent = null;
public de.amberhome.objects.appcompat.ACToolbarLightWrapper _actionbar = null;
public lab.tgna.main _main = null;
public lab.tgna.home _home = null;
public lab.tgna.slidingpanels _slidingpanels = null;
public lab.tgna.video _video = null;
public lab.tgna.biodata _biodata = null;
public lab.tgna.starter _starter = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _actionbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 197;BA.debugLine="Sub ActionBar_NavigationItemClick";
 //BA.debugLineNum = 198;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
String _text = "";
int _i = 0;
 //BA.debugLineNum = 50;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 52;BA.debugLine="MediaPlayer1.Initialize( )";
_mediaplayer1.Initialize();
 //BA.debugLineNum = 53;BA.debugLine="MediaPlayer1.Load(File.DirAssets, \"lagu2.mp3\")";
_mediaplayer1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"lagu2.mp3");
 //BA.debugLineNum = 54;BA.debugLine="timer1.Initialize(\"timer1\", 1000)";
_timer1.Initialize(processBA,"timer1",(long) (1000));
 //BA.debugLineNum = 55;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 57;BA.debugLine="Activity.LoadLayout(\"main2\")";
mostCurrent._activity.LoadLayout("main2",mostCurrent.activityBA);
 //BA.debugLineNum = 58;BA.debugLine="pContent.LoadLayout(\"flip3dview1\")";
mostCurrent._pcontent.LoadLayout("flip3dview1",mostCurrent.activityBA);
 //BA.debugLineNum = 59;BA.debugLine="ActionBar.Title = \"KATA MUTIARA TGNA\"";
mostCurrent._actionbar.setTitle((java.lang.CharSequence)("KATA MUTIARA TGNA"));
 //BA.debugLineNum = 60;BA.debugLine="ActionBar.LogoBitmap = LoadBitmap(File.DirAssets,";
mostCurrent._actionbar.setLogoBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"tgna.png").getObject()));
 //BA.debugLineNum = 61;BA.debugLine="ActionBar.SubTitle = \"\"";
mostCurrent._actionbar.setSubTitle((java.lang.CharSequence)(""));
 //BA.debugLineNum = 62;BA.debugLine="ABHelper.Initialize";
mostCurrent._abhelper.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 63;BA.debugLine="ABHelper.ShowUpIndicator = True";
mostCurrent._abhelper.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 64;BA.debugLine="ActionBar.InitMenuListener";
mostCurrent._actionbar.InitMenuListener();
 //BA.debugLineNum = 67;BA.debugLine="PageTurner.Initialize(\"PageTurner\", 20)";
mostCurrent._pageturner.Initialize(mostCurrent.activityBA,"PageTurner",(int) (20));
 //BA.debugLineNum = 68;BA.debugLine="pContent.AddView(PageTurner, 10dip, 10dip, pConte";
mostCurrent._pcontent.AddView((android.view.View)(mostCurrent._pageturner.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (mostCurrent._pcontent.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),(int) (mostCurrent._pcontent.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
 //BA.debugLineNum = 70;BA.debugLine="If pContent.Width > pContent.Height Then";
if (mostCurrent._pcontent.getWidth()>mostCurrent._pcontent.getHeight()) { 
 //BA.debugLineNum = 71;BA.debugLine="PageTurner.TwoPages = True ' the default is Fals";
mostCurrent._pageturner.setTwoPages(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 72;BA.debugLine="PageTurner.RenderLeftPage = True ' the default i";
mostCurrent._pageturner.setRenderLeftPage(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 73;BA.debugLine="Pager.SetPageParameters(Pager.ALIGN_NORMAL, Page";
mostCurrent._pager.SetPageParameters(mostCurrent._pager.ALIGN_NORMAL,(int) (mostCurrent._pageturner.getWidth()/(double)2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))),_leftmargin,(int) (mostCurrent._pageturner.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))),_topmargin,(float) (_linespacing));
 //BA.debugLineNum = 74;BA.debugLine="PageTurner.SetMarginPixels(6dip, 6dip, 6dip, 6di";
mostCurrent._pageturner.SetMarginPixels(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6)));
 }else {
 //BA.debugLineNum = 76;BA.debugLine="PageTurner.TwoPages = False";
mostCurrent._pageturner.setTwoPages(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 77;BA.debugLine="PageTurner.RenderLeftPage = False";
mostCurrent._pageturner.setRenderLeftPage(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 78;BA.debugLine="Pager.SetPageParameters(Pager.ALIGN_CENTER, Page";
mostCurrent._pager.SetPageParameters(mostCurrent._pager.ALIGN_CENTER,(int) (mostCurrent._pageturner.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),_leftmargin,(int) (mostCurrent._pageturner.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),_topmargin,(float) (_linespacing));
 //BA.debugLineNum = 79;BA.debugLine="PageTurner.SetMarginPixels(10dip, 10dip, 10dip,";
mostCurrent._pageturner.SetMarginPixels(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 };
 //BA.debugLineNum = 81;BA.debugLine="PageTurner.AllowLastPageCurl = False ' the defaul";
mostCurrent._pageturner.setAllowLastPageCurl(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 83;BA.debugLine="Font = Font.CreateNew(Typeface.DEFAULT, Typeface.";
mostCurrent._font.setObject((android.graphics.Typeface)(mostCurrent._font.CreateNew(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,anywheresoftware.b4a.keywords.Common.Typeface.STYLE_BOLD)));
 //BA.debugLineNum = 84;BA.debugLine="Dim text As String";
_text = "";
 //BA.debugLineNum = 85;BA.debugLine="For i = 0 To paracount - 1";
{
final int step29 = 1;
final int limit29 = (int) (_paracount-1);
for (_i = (int) (0) ; (step29 > 0 && _i <= limit29) || (step29 < 0 && _i >= limit29); _i = ((int)(0 + _i + step29)) ) {
 //BA.debugLineNum = 86;BA.debugLine="text = text & paragraph & CRLF & CRLF & CRLF & C";
_text = _text+_paragraph+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 87;BA.debugLine="text = text & paragraph2 & CRLF & CRLF & CRLF &";
_text = _text+_paragraph2+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"(MUTIARA 1) "+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 88;BA.debugLine="text = text & paragraph3   & CRLF & CRLF   & \"(M";
_text = _text+_paragraph3+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"(MUTIARA 2) "+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 89;BA.debugLine="text = text & paragraph4 & CRLF & CRLF & CRLF  &";
_text = _text+_paragraph4+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"(MUTIARA 3) "+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 90;BA.debugLine="text = text & paragraph5 & CRLF & CRLF & CRLF &";
_text = _text+_paragraph5+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"(MUTIARA 4) "+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 91;BA.debugLine="text = text & paragraph6 & CRLF & CRLF & CRLF &";
_text = _text+_paragraph6+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"(MUTIARA 5) "+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 92;BA.debugLine="text = text & paragraph7 & CRLF & CRLF & CRLF &";
_text = _text+_paragraph7+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"(MUTIARA 6) "+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 93;BA.debugLine="text = text & paragraph8 & CRLF   & \"(MUTIARA 7)";
_text = _text+_paragraph8+anywheresoftware.b4a.keywords.Common.CRLF+"(MUTIARA 7) "+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 94;BA.debugLine="text = text & paragraph9 & CRLF & CRLF    & \"(MU";
_text = _text+_paragraph9+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"(MUTIARA 8) "+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 95;BA.debugLine="text = text & paragraph10 & CRLF & CRLF    & \"(M";
_text = _text+_paragraph10+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"(MUTIARA 9) "+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 96;BA.debugLine="text = text & paragraph11 & CRLF & CRLF    & \"(M";
_text = _text+_paragraph11+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"(MUTIARA 10) "+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 }
};
 //BA.debugLineNum = 98;BA.debugLine="Pager.SetPaintParameters(Font, 20, Colors.Black,";
mostCurrent._pager.SetPaintParameters(processBA,(android.graphics.Typeface)(mostCurrent._font.getObject()),(float) (20),anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 99;BA.debugLine="Pager.Paginate(text)";
mostCurrent._pager.Paginate(_text);
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 115;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 116;BA.debugLine="PageNumber = PageTurner.CurrentPage";
_pagenumber = mostCurrent._pageturner.getCurrentPage();
 //BA.debugLineNum = 117;BA.debugLine="PageTurner.OnPause";
mostCurrent._pageturner.OnPause();
 //BA.debugLineNum = 118;BA.debugLine="If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause";
if (_mediaplayer1.IsPlaying()) { 
_mediaplayer1.Pause();};
 //BA.debugLineNum = 119;BA.debugLine="timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 120;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 103;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 104;BA.debugLine="PageTurner.CurrentPage = PageNumber";
mostCurrent._pageturner.setCurrentPage(_pagenumber);
 //BA.debugLineNum = 105;BA.debugLine="PageTurner.Color = Colors.LightGray ' otherwise i";
mostCurrent._pageturner.setColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 106;BA.debugLine="PageTurner.OnResume";
mostCurrent._pageturner.OnResume();
 //BA.debugLineNum = 108;BA.debugLine="MediaPlayer1.Play";
_mediaplayer1.Play();
 //BA.debugLineNum = 109;BA.debugLine="timer1.Enabled = True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 110;BA.debugLine="timer1_Tick 'don't wait one second for the UI to";
_timer1_tick();
 //BA.debugLineNum = 111;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return "";
}
public static String  _converttotimeformat(int _ms) throws Exception{
int _seconds = 0;
int _minutes = 0;
 //BA.debugLineNum = 136;BA.debugLine="Sub ConvertToTimeFormat(ms As Int) As String";
 //BA.debugLineNum = 137;BA.debugLine="Dim seconds, minutes As Int";
_seconds = 0;
_minutes = 0;
 //BA.debugLineNum = 138;BA.debugLine="seconds = Round(ms / 1000)";
_seconds = (int) (anywheresoftware.b4a.keywords.Common.Round(_ms/(double)1000));
 //BA.debugLineNum = 139;BA.debugLine="minutes = Floor(seconds / 60)";
_minutes = (int) (anywheresoftware.b4a.keywords.Common.Floor(_seconds/(double)60));
 //BA.debugLineNum = 140;BA.debugLine="seconds = seconds Mod 60";
_seconds = (int) (_seconds%60);
 //BA.debugLineNum = 141;BA.debugLine="Return NumberFormat(minutes, 1, 0) & \":\" & Number";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (1),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_seconds,(int) (2),(int) (0));
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 35;BA.debugLine="Dim PageTurner As PageTurnView";
mostCurrent._pageturner = new anywheresoftware.b4a.agraham.pageturnview.CurlViewWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim Pager As TextPaginator";
mostCurrent._pager = new anywheresoftware.b4a.agraham.pageturnview.CurlViewWrapper.TextPaginator();
 //BA.debugLineNum = 37;BA.debugLine="Dim Font As Typeface";
mostCurrent._font = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim Bitmap1 As Bitmap";
mostCurrent._bitmap1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim DestRect As Rect";
mostCurrent._destrect = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim  introwel As MediaPlayer";
mostCurrent._introwel = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private btnHome As Button";
mostCurrent._btnhome = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim AC As AppCompat";
mostCurrent._ac = new de.amberhome.objects.appcompat.AppCompatBase();
 //BA.debugLineNum = 44;BA.debugLine="Dim ABHelper As ACActionBar";
mostCurrent._abhelper = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 45;BA.debugLine="Private pContent As Panel";
mostCurrent._pcontent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private ActionBar As ACToolBarLight";
mostCurrent._actionbar = new de.amberhome.objects.appcompat.ACToolbarLightWrapper();
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _pageturner_getbitmap(int _width,int _height,int _page) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cnv = null;
 //BA.debugLineNum = 146;BA.debugLine="Sub PageTurner_GetBitmap(Width As Int, Height As I";
 //BA.debugLineNum = 147;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 148;BA.debugLine="Dim cnv As Canvas";
_cnv = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 149;BA.debugLine="bmp.InitializeMutable(Width, Height)";
_bmp.InitializeMutable(_width,_height);
 //BA.debugLineNum = 150;BA.debugLine="Try";
try { //BA.debugLineNum = 152;BA.debugLine="If Page = 0 Then";
if (_page==0) { 
 //BA.debugLineNum = 153;BA.debugLine="cnv.Initialize2(bmp)";
_cnv.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 154;BA.debugLine="cnv.DrawColor(Colors.DarkGray)";
_cnv.DrawColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 155;BA.debugLine="Bitmap1.Initialize(File.DirAssets, \"3.jpg\")";
mostCurrent._bitmap1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"3.jpg");
 //BA.debugLineNum = 156;BA.debugLine="DestRect.Initialize(10dip, 130dip, 10dip + 300d";
mostCurrent._destrect.Initialize(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (400))));
 //BA.debugLineNum = 157;BA.debugLine="cnv.DrawBitmap(Bitmap1, Null, DestRect)";
_cnv.DrawBitmap((android.graphics.Bitmap)(mostCurrent._bitmap1.getObject()),(android.graphics.Rect)(anywheresoftware.b4a.keywords.Common.Null),(android.graphics.Rect)(mostCurrent._destrect.getObject()));
 //BA.debugLineNum = 158;BA.debugLine="cnv.DrawText( \"(10 Kata-Kata Mutiara TGNA) \", W";
_cnv.DrawText(mostCurrent.activityBA,"(10 Kata-Kata Mutiara TGNA) ",(float) (_width/(double)2),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (24),anywheresoftware.b4a.keywords.Common.Colors.White,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 159;BA.debugLine="Return bmp";
if (true) return _bmp;
 }else if(_page==mostCurrent._pager.getPageCount()+1) { 
 //BA.debugLineNum = 161;BA.debugLine="cnv.Initialize2(bmp)";
_cnv.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 162;BA.debugLine="cnv.DrawColor(Colors.DarkGray)";
_cnv.DrawColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 163;BA.debugLine="cnv.DrawText( \"صدق الله العظيم\", Width/2, 100di";
_cnv.DrawText(mostCurrent.activityBA,"صدق الله العظيم",(float) (_width/(double)2),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (24),anywheresoftware.b4a.keywords.Common.Colors.White,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 164;BA.debugLine="Return bmp";
if (true) return _bmp;
 }else {
 //BA.debugLineNum = 166;BA.debugLine="Return Pager.GetPageBitmap(Page - 1, Colors.Whi";
if (true) return (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(mostCurrent._pager.GetPageBitmap((int) (_page-1),anywheresoftware.b4a.keywords.Common.Colors.White)));
 };
 } 
       catch (Exception e22) {
			processBA.setLastException(e22); //BA.debugLineNum = 170;BA.debugLine="PTException";
_ptexception();
 };
 //BA.debugLineNum = 172;BA.debugLine="Return bmp";
if (true) return _bmp;
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return null;
}
public static int  _pageturner_getpages() throws Exception{
 //BA.debugLineNum = 175;BA.debugLine="Sub PageTurner_GetPages() As Int";
 //BA.debugLineNum = 176;BA.debugLine="Try";
try { //BA.debugLineNum = 177;BA.debugLine="Return Pager.PageCount + 2";
if (true) return (int) (mostCurrent._pager.getPageCount()+2);
 } 
       catch (Exception e4) {
			processBA.setLastException(e4); //BA.debugLineNum = 179;BA.debugLine="Return 0";
if (true) return (int) (0);
 };
 //BA.debugLineNum = 181;BA.debugLine="End Sub";
return 0;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="Dim PageNumber As Int";
_pagenumber = 0;
 //BA.debugLineNum = 11;BA.debugLine="Dim MediaPlayer1 As MediaPlayer";
_mediaplayer1 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 14;BA.debugLine="Dim paragraph,paragraph2,paragraph3,paragraph4,pa";
_paragraph = "";
_paragraph2 = "";
_paragraph3 = "";
_paragraph4 = "";
_paragraph5 = "";
_paragraph6 = "";
_paragraph7 = "";
_paragraph8 = "";
_paragraph9 = "";
_paragraph10 = "";
_paragraph11 = "";
 //BA.debugLineNum = 15;BA.debugLine="paragraph=\"بِسْمِ اللهِ الرَّحْمنِ الرَّحِيمِ \"";
_paragraph = "بِسْمِ اللهِ الرَّحْمنِ الرَّحِيمِ ";
 //BA.debugLineNum = 16;BA.debugLine="paragraph2=\"“Jika kereta rosak, mekanik yang akan";
_paragraph2 = "“Jika kereta rosak, mekanik yang akan membaikinya. Jika tubuh badan bermasalah, doktor yang akan mengubatinya. Jika kehidupan manusia bermasalah, maka Pencipta manusia itulah yang perlu dirujuk. Pelik sungguh manusia yang mengaku Allah SWT sebagai tuhan tetapi enggan merujuk kepada syariat Allah bila berdepan dengan masalah...“";
 //BA.debugLineNum = 17;BA.debugLine="paragraph3=\"“Perbezaan yang paling besar di antar";
_paragraph3 = "“Perbezaan yang paling besar di antara manusia dengan makhluk yang lain ialah ilmu. Manusia diberikan kebolehan untuk diajar dan mengajar. Binatang ternakan contohnya, boleh diajar untuk mematuhi perintah tuannya, tetapi tidak boleh mengembangkan ilmu tersebut kepada binatang yang lain. Berbeza dengan manusia, sedikit ilmu yang diperolehinya mampu dikembangan dengan lebih meluas..“";
 //BA.debugLineNum = 18;BA.debugLine="paragraph4=\"“Kita tanam lada, 40 hari dapat hasil";
_paragraph4 = "“Kita tanam lada, 40 hari dapat hasil, boleh jual. Tanam terung, dua tiga bulan dapat hasil. Tapi keuntungan tidak besar kerana itu hanya projek kecil-kecilan. Jika projek yang lebih besar seperti tanam getah, bukan 40 hari tapi memakan masa bertahun. Semakin besar projek tersebut, semakin besar untungnya. Demikianlah juga pahala yang merupakan projek paling besar. Untungnya sangat besar jadi padanlah dengan menunggunya begitu lama iaitu selepas mati baru mendapat hasil..“";
 //BA.debugLineNum = 19;BA.debugLine="paragraph5=\"“Jika memandu kita perlukan lesen, me";
_paragraph5 = "“Jika memandu kita perlukan lesen, menggunakan senjata api perlukan lesen, maka hidup di dunia juga tentulah perlukan lesen. Lesen itu ialah Islam. Hidup di dunia dengan menolak Islam ialah umpama hidup tanpa lesen...”";
 //BA.debugLineNum = 20;BA.debugLine="paragraph6=\"“ Bila kita beragama, dengan sendiri";
_paragraph6 = "“ Bila kita beragama, dengan sendiri kita akan merasa bahawa disisi kita ada malaikat yang memerhati, ada Allah SWT sedang memantau...Keyakinan ini merupakan satu brek untuk kita terus lakukan dosa...tanpa ada yakin seperti ini manusia akan hilang brek...kalau kereta hilang brek, ia akan terjun gaung, tapi kalau manusia hilang brek, dia akan terjun Neraka....“";
 //BA.debugLineNum = 21;BA.debugLine="paragraph7=\"”Pemuda adalah batu-bata dalam perjua";
_paragraph7 = "”Pemuda adalah batu-bata dalam perjuangan Islam, kerana itu pemuda mesti lengkapkan diri dengan ilmu dan perbanyakkan amalan sunat...saya impikan untuk menjadi makmum dimana pemuda imamnya, pendengar kuliah yang disampaikan oleh pemuda, saya hanya mahu datang dan dengar...”";
 //BA.debugLineNum = 22;BA.debugLine="paragraph8=\"“Allah menciptakan di dunia ini orang";
_paragraph8 = "“Allah menciptakan di dunia ini orang kurang upaya untuk menjadi ujian kepada orang yang sempurna. Di akhirat kelak akan ditanya, setakat manakah kita orang-orang yang cukup upaya ini menghulurkan bantuan kepada mereka yang kurang upaya. Sebab itu, saudara-saudara kita yang kurang upaya sedikit untung kerana terlepas daripada soalan ini di akhirat nanti. Manakala yang sempurna tubuh badan pula perlulah pastikan duit yang dia ada pada hari ini sedikit sebanyak dia peruntukkan untuk beli pahala lain selain beli lauk-pauk, beras dan keperluan hidup...“";
 //BA.debugLineNum = 23;BA.debugLine="paragraph9=\"“Jika anak-anak kita akan menghadapi";
_paragraph9 = "“Jika anak-anak kita akan menghadapi peperiksaan, kita akan berpesan kepadanya, jawab sebagaimana yang cikgu ajar. Jika betul, lulus. Jika salah jawabnya gagal. Di akhirat kelak, peperiksaan di hadapan Allah SWT nanti juga memerlukan kepada jawapan yang sama dengan cikgu ajar. Cikgu itu ialah Rasulullah SAW yang diutuskan oleh Allah SWT. Jawab seperti yang cikgu ajar, syurga balasannya. Gagal menjawab seperti yang diajar cikgu, nerakalah balasannya...“";
 //BA.debugLineNum = 24;BA.debugLine="paragraph10=\"”Walaupun malam gelap dan di bawah m";
_paragraph10 = "”Walaupun malam gelap dan di bawah meja, semua kerja-kerja sulit akan didedahkan apabila kita berhadapan dengan Allah SWT di akhirat kelak. Baik manusia hebat mana pun, semuanya akan didedah rahsianya nanti. Keyakinan seperti ini boleh menyebabkan manusia terbantut untuk buat maksiat..”";
 //BA.debugLineNum = 25;BA.debugLine="paragraph11=\"”Nyawa kita diumpamakan pelita yang";
_paragraph11 = "”Nyawa kita diumpamakan pelita yang pada bila-bila masa sahaja boleh terpadam..”";
 //BA.debugLineNum = 27;BA.debugLine="Dim paracount As Int : paracount = 1";
_paracount = 0;
 //BA.debugLineNum = 27;BA.debugLine="Dim paracount As Int : paracount = 1";
_paracount = (int) (1);
 //BA.debugLineNum = 28;BA.debugLine="Dim LeftMargin As Int : LeftMargin = 10dip";
_leftmargin = 0;
 //BA.debugLineNum = 28;BA.debugLine="Dim LeftMargin As Int : LeftMargin = 10dip";
_leftmargin = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10));
 //BA.debugLineNum = 29;BA.debugLine="Dim TopMargin As Int : TopMargin = 10dip";
_topmargin = 0;
 //BA.debugLineNum = 29;BA.debugLine="Dim TopMargin As Int : TopMargin = 10dip";
_topmargin = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10));
 //BA.debugLineNum = 30;BA.debugLine="Dim LineSpacing As Int : LineSpacing = 1";
_linespacing = 0;
 //BA.debugLineNum = 30;BA.debugLine="Dim LineSpacing As Int : LineSpacing = 1";
_linespacing = (int) (1);
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _ptexception() throws Exception{
anywheresoftware.b4a.agraham.threading.Threading.ExceptionWrapper _ex = null;
String _where = "";
Object[] _args = null;
 //BA.debugLineNum = 183;BA.debugLine="Sub PTException()";
 //BA.debugLineNum = 184;BA.debugLine="Dim Ex As ExceptionEx";
_ex = new anywheresoftware.b4a.agraham.threading.Threading.ExceptionWrapper();
 //BA.debugLineNum = 185;BA.debugLine="Dim where As String";
_where = "";
 //BA.debugLineNum = 186;BA.debugLine="Ex = LastException";
_ex.setObject((java.lang.Exception)(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getObject()));
 //BA.debugLineNum = 187;BA.debugLine="Dim args(2) As Object";
_args = new Object[(int) (2)];
{
int d0 = _args.length;
for (int i0 = 0;i0 < d0;i0++) {
_args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 188;BA.debugLine="args(0) = LastException.Message";
_args[(int) (0)] = (Object)(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage());
 //BA.debugLineNum = 189;BA.debugLine="where = Ex.StackTraceElement(2)";
_where = _ex.StackTraceElement((int) (2));
 //BA.debugLineNum = 190;BA.debugLine="args(1) = where";
_args[(int) (1)] = (Object)(_where);
 //BA.debugLineNum = 191;BA.debugLine="PageTurner.RunOnGuiThread(\"ShowPTError\", args)";
mostCurrent._pageturner.RunOnGuiThread("ShowPTError",_args);
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
public static String  _showpterror(String _title,String _msg) throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub ShowPTError(title As String, msg As String)";
 //BA.debugLineNum = 124;BA.debugLine="Msgbox(msg, title)";
anywheresoftware.b4a.keywords.Common.Msgbox(_msg,_title,mostCurrent.activityBA);
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Sub timer1_Tick";
 //BA.debugLineNum = 129;BA.debugLine="If MediaPlayer1.IsPlaying Then";
if (_mediaplayer1.IsPlaying()) { 
 };
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
}
