Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region Module Attributes
	#FullScreen: False
	#IncludeTitle: True
#End Region
#Extends: android.support.v7.app.AppCompatActivity

Sub Process_Globals
Dim tmrAnimation As Timer
Dim currentPanelBeforePaused As Int
Dim tmrSlider As Timer

Dim MediaPlayer1 As MediaPlayer

	Dim timer1 As Timer
End Sub

Sub Globals
	Dim bmp0, bmp1, bmp2, bmp3 As BitmapDrawable
    Dim sd As SlidingData
	Dim startX, startY As Float
	Dim SlidingDuration As Int
	SlidingDuration = 700
	Dim offsetX As Int = 45%x
	Dim imgs(5) As ImageView
	Dim cd , cd2 As ColorDrawable
	Dim  introwel As MediaPlayer 
	Private btnKata As Button

	Dim BtnCreate As Button

	Private btnVideo As Button
		Dim AC As AppCompat
	Dim ABHelper As ACActionBar
	Private pContent As Panel
Private ActionBar As ACToolBarLight


	Private btnBiodata As Button

End Sub

Sub Activity_Create(FirstTime As Boolean)

	
	MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "lagu.mp3")
		timer1.Initialize("timer1", 1000)
		MediaPlayer1.Looping = True
		
		
	Activity.LoadLayout("main2")
	pContent.LoadLayout("main")
	ActionBar.Title = "TUAN GURU NIK AZIZ"
	ActionBar.SubTitle = ""
	ActionBar.LogoBitmap = LoadBitmap(File.DirAssets, "tgna.png")
	Activity.AddMenuItem("Keluar", "Menu")
	
	
	Dim panels(5) As Panel
	cd.Initialize(Colors.black,10dip)
	cd2.Initialize(Colors.DarkGray,10dip)
	For i = 0 To 4
	  panels(i).Initialize("panels")
	  imgs(i).Initialize("imgs")
	  pContent.Color=Colors.White
	  pContent.AddView(panels(i),0%x,0%y,100%x,95%y)
	  panels(i).SetBackgroundImage(LoadBitmapSample(File.DirAssets,(i+1)&".jpg",panels(i).Width,panels(i).Height))
	  pContent.AddView(imgs(i),offsetX,(panels(i).Top + panels(i).Height) + 5dip,10dip,10dip)
	  If i = 0 Then
	  	pContent.LoadLayout("main")
	  	  imgs(i).Background = cd2
	  Else
	  	pContent.LoadLayout("main")
		  imgs(i).Background = cd
	  End If
	  offsetX = offsetX + 10dip
	Next
	sd.Initialize
	sd.panels = panels
	SlidingPanels.Initialize(sd, SlidingDuration)
	sd.targetPanel = -1
	sd.currentPanel = currentPanelBeforePaused - 1
	ChangePanel(True)
	tmrSlider.Initialize("tmrSlider",5000)
	tmrSlider.Enabled = True
	

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

Sub timer1_Tick
	If MediaPlayer1.IsPlaying Then

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



Sub tmrSlider_Tick
 ChangePanel(True)
 For i = 0 To 4
 If i = sd.currentPanel Then
   imgs(i).Background = cd2
   Else
   imgs(i).Background = cd
  End If
 Next
End Sub


Sub ChangePanel(Left As Boolean)
	SlidingPanels.ChangePanel(sd, Left)
End Sub

Sub Animation1_AnimationEnd
	SlidingPanels.AnimationEnd(sd)
	If sd.targetPanel >= 0 Then 
		tmrAnimation.Enabled = True
		Return 
	End If
End Sub
Sub tmrAnimation_Tick
	tmrAnimation.Enabled = False
	ContinueJumping
End Sub

Sub JumpToPanel (Target As Int)
	sd.targetPanel = Target
	For i = 0 To 1
		sd.leftAnimations(i).Duration = SlidingDuration / 2
		sd.rightAnimations(i).Duration = SlidingDuration / 2
	Next
	ContinueJumping
End Sub
Sub ContinueJumping
	If sd.targetPanel < 0 Or sd.targetPanel = sd.currentPanel Then 
		sd.targetPanel = -1
		Animation1_AnimationEnd
		For i = 0 To 1
			sd.leftAnimations(i).Duration = SlidingDuration
			sd.rightAnimations(i).Duration = SlidingDuration
		Next
		Return
	End If
	SlidingPanels.ChangePanel(sd, sd.targetPanel > sd.currentPanel)
End Sub

Sub Panels_Touch (Action As Int, X As Float, Y As Float)
	Select Action
		Case pContent.ACTION_DOWN
			startX = X
			startY = Y
		Case pContent.ACTION_UP
			If Abs(Y - startY) > 20%y Then Return
			If X - startX > 30%x  Then 
				ChangePanel(False)
			Else If startX - X > 30%x Then
				ChangePanel(True)
			End If
	End Select
End Sub



Sub btnKata_Click
	StartActivity("kata")
End Sub

Sub btnVideo_Click

	StartActivity("video")
End Sub

Sub btnBiodata_Click
	StartActivity("biodata")
End Sub

Sub Menu_Click()
Dim bmp As Bitmap
Dim choice As Int
bmp.Initialize(File.DirAssets, "help.png")
choice = Msgbox2(" Keluar sekarang?", "Pengesahan ", "Ya", "", "Tidak", bmp)
If choice = DialogResponse.POSITIVE Then 
ExitApplication 
  
End If  
End Sub