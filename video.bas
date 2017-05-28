Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region Module Attributes
	#FullScreen: true
	#IncludeTitle: false
#End Region
#Extends: android.support.v7.app.AppCompatActivity

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
'---===== >> Basic4WinDroid.ir <<=====------
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
Dim vv As VideoView
Dim Send As ImageView
	Private btnHome As Button

Dim AC As AppCompat
	Dim ABHelper As ACActionBar
	Private pContent As Panel
Private ActionBar As ACToolBarLight

	Dim a As ICOSFlip3DView
	Dim i1 As ImageView
		Dim p As Phone
	Dim offsetX As Int = 45%x
End Sub


Sub Activity_Create(FirstTime As Boolean)
'tamam hoghogh in proje motaalegh be anjomane Basic4windroid.ir mibashad
'baraye rahnamai be ma bepeyvandid  ---===== >> Basic4WinDroid.ir <<=====------
	
	 p.SetScreenOrientation(0)
	 
	Activity.LoadLayout("main2")
	pContent.LoadLayout("video")
	ActionBar.Title = "KULIAH TGNA"
	ActionBar.SubTitle = ""

Dim panels(5) As Panel
	
vv.Initialize("vv")
'pContent.AddView(vv, 00dip, 00dip, 1200dip, 480dip)
Activity.AddView(vv,0%x,0%y,100%x,100%x)
File.Exists(File.DirDefaultExternal, "video.mp4")
vv.LoadVideo(File.DirRootExternal, "video.mp4") 
vv.Play
   Dim TargetDir As String = File.DirRootExternal
   If File.Exists(TargetDir, "video.mp4") = False Then
      File.Copy(File.DirAssets, "video.mp4", TargetDir, "video.mp4")
   End If
   Return TargetDir
End Sub
Sub Activity_Resume
'---===== >> Basic4WinDroid.ir <<=====------
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub esci_Click
ExitApplication
End Sub
