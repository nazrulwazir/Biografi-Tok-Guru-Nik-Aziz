Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region
#Extends: android.support.v7.app.AppCompatActivity
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
Dim MediaPlayer1 As MediaPlayer

	Dim timer1 As Timer

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim AC As AppCompat
	Dim ABHelper As ACActionBar
	Private pContent As Panel
Private ActionBar As ACToolBarLight

	Dim scvTest As ScrollView
	Dim pnlTest As Panel
End Sub

Sub Activity_Create(FirstTime As Boolean)

MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "surah.mp3")
		timer1.Initialize("timer1", 1000)
		MediaPlayer1.Looping = True
		
		
Activity.LoadLayout("main3")
	
	scvTest.Panel.LoadLayout("biodata")
	scvTest.Panel.Height = pnlTest.Height

	
		ActionBar.Title = "BIODATA TGNA"
	ActionBar.SubTitle = ""
	ABHelper.Initialize
ABHelper.ShowUpIndicator = True
ActionBar.InitMenuListener
ActionBar.LogoBitmap = LoadBitmap(File.DirAssets, "tgna.png")

End Sub

Sub Activity_Resume
	MediaPlayer1.Play
	timer1.Enabled = True
	timer1_Tick 'don't wait one second for the UI to update.
	MediaPlayer1.Looping = True
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause
	timer1.Enabled = False
	MediaPlayer1.Looping = True
End Sub

Sub ShowPTError(title As String, msg As String)
	Msgbox(msg, title)
	
End Sub

Sub timer1_Tick
	If MediaPlayer1.IsPlaying Then
		'barPosition.Value = MediaPlayer1.Position / MediaPlayer1.Duration * 100
		'lblPosition.Text = "Position: " & ConvertToTimeFormat(MediaPlayer1.Position) & _
		'	" (" & ConvertToTimeFormat(MediaPlayer1.Duration) & ")"
	End If
End Sub
'converts milliseconds to m:ss format.
Sub ConvertToTimeFormat(ms As Int) As String
	Dim seconds, minutes As Int
	seconds = Round(ms / 1000)
	minutes = Floor(seconds / 60)
	seconds = seconds Mod 60
	Return NumberFormat(minutes, 1, 0) & ":" & NumberFormat(seconds, 2, 0) 'ex: 3:05
End Sub

Sub ActionBar_NavigationItemClick
   Activity.Finish
End Sub
