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
'Activity module
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim PageNumber As Int
	Dim MediaPlayer1 As MediaPlayer

	Dim timer1 As Timer
	Dim paragraph,paragraph2,paragraph3,paragraph4,paragraph5,paragraph6,paragraph7,paragraph8,paragraph9,paragraph10,paragraph11 As String
	paragraph="بِسْمِ اللهِ الرَّحْمنِ الرَّحِيمِ "
	paragraph2="“Jika kereta rosak, mekanik yang akan membaikinya. Jika tubuh badan bermasalah, doktor yang akan mengubatinya. Jika kehidupan manusia bermasalah, maka Pencipta manusia itulah yang perlu dirujuk. Pelik sungguh manusia yang mengaku Allah SWT sebagai tuhan tetapi enggan merujuk kepada syariat Allah bila berdepan dengan masalah...“"
	paragraph3="“Perbezaan yang paling besar di antara manusia dengan makhluk yang lain ialah ilmu. Manusia diberikan kebolehan untuk diajar dan mengajar. Binatang ternakan contohnya, boleh diajar untuk mematuhi perintah tuannya, tetapi tidak boleh mengembangkan ilmu tersebut kepada binatang yang lain. Berbeza dengan manusia, sedikit ilmu yang diperolehinya mampu dikembangan dengan lebih meluas..“"
	paragraph4="“Kita tanam lada, 40 hari dapat hasil, boleh jual. Tanam terung, dua tiga bulan dapat hasil. Tapi keuntungan tidak besar kerana itu hanya projek kecil-kecilan. Jika projek yang lebih besar seperti tanam getah, bukan 40 hari tapi memakan masa bertahun. Semakin besar projek tersebut, semakin besar untungnya. Demikianlah juga pahala yang merupakan projek paling besar. Untungnya sangat besar jadi padanlah dengan menunggunya begitu lama iaitu selepas mati baru mendapat hasil..“"
	paragraph5="“Jika memandu kita perlukan lesen, menggunakan senjata api perlukan lesen, maka hidup di dunia juga tentulah perlukan lesen. Lesen itu ialah Islam. Hidup di dunia dengan menolak Islam ialah umpama hidup tanpa lesen...”"
	paragraph6="“ Bila kita beragama, dengan sendiri kita akan merasa bahawa disisi kita ada malaikat yang memerhati, ada Allah SWT sedang memantau...Keyakinan ini merupakan satu brek untuk kita terus lakukan dosa...tanpa ada yakin seperti ini manusia akan hilang brek...kalau kereta hilang brek, ia akan terjun gaung, tapi kalau manusia hilang brek, dia akan terjun Neraka....“"
	paragraph7="”Pemuda adalah batu-bata dalam perjuangan Islam, kerana itu pemuda mesti lengkapkan diri dengan ilmu dan perbanyakkan amalan sunat...saya impikan untuk menjadi makmum dimana pemuda imamnya, pendengar kuliah yang disampaikan oleh pemuda, saya hanya mahu datang dan dengar...”"
	paragraph8="“Allah menciptakan di dunia ini orang kurang upaya untuk menjadi ujian kepada orang yang sempurna. Di akhirat kelak akan ditanya, setakat manakah kita orang-orang yang cukup upaya ini menghulurkan bantuan kepada mereka yang kurang upaya. Sebab itu, saudara-saudara kita yang kurang upaya sedikit untung kerana terlepas daripada soalan ini di akhirat nanti. Manakala yang sempurna tubuh badan pula perlulah pastikan duit yang dia ada pada hari ini sedikit sebanyak dia peruntukkan untuk beli pahala lain selain beli lauk-pauk, beras dan keperluan hidup...“"
	paragraph9="“Jika anak-anak kita akan menghadapi peperiksaan, kita akan berpesan kepadanya, jawab sebagaimana yang cikgu ajar. Jika betul, lulus. Jika salah jawabnya gagal. Di akhirat kelak, peperiksaan di hadapan Allah SWT nanti juga memerlukan kepada jawapan yang sama dengan cikgu ajar. Cikgu itu ialah Rasulullah SAW yang diutuskan oleh Allah SWT. Jawab seperti yang cikgu ajar, syurga balasannya. Gagal menjawab seperti yang diajar cikgu, nerakalah balasannya...“"
	paragraph10="”Walaupun malam gelap dan di bawah meja, semua kerja-kerja sulit akan didedahkan apabila kita berhadapan dengan Allah SWT di akhirat kelak. Baik manusia hebat mana pun, semuanya akan didedah rahsianya nanti. Keyakinan seperti ini boleh menyebabkan manusia terbantut untuk buat maksiat..”"
	paragraph11="”Nyawa kita diumpamakan pelita yang pada bila-bila masa sahaja boleh terpadam..”"

	Dim paracount As Int : paracount = 1
	Dim LeftMargin As Int : LeftMargin = 10dip
	Dim TopMargin As Int : TopMargin = 10dip
	Dim LineSpacing As Int : LineSpacing = 1
End Sub

Sub Globals

	Dim PageTurner As PageTurnView
	Dim Pager As TextPaginator
	Dim Font As Typeface
	Dim Bitmap1 As Bitmap
Dim DestRect As Rect
	Dim  introwel As MediaPlayer 
	Private btnHome As Button
	
Dim AC As AppCompat
	Dim ABHelper As ACActionBar
	Private pContent As Panel
Private ActionBar As ACToolBarLight
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Activity.LoadLayout("flip3dview1")
	MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "lagu2.mp3")
		timer1.Initialize("timer1", 1000)
		MediaPlayer1.Looping = True
		
	Activity.LoadLayout("main2")
	pContent.LoadLayout("flip3dview1")
	ActionBar.Title = "KATA MUTIARA TGNA"
	ActionBar.LogoBitmap = LoadBitmap(File.DirAssets, "tgna.png")
	ActionBar.SubTitle = ""
	ABHelper.Initialize
ABHelper.ShowUpIndicator = True
ActionBar.InitMenuListener
	

	PageTurner.Initialize("PageTurner", 20)
	pContent.AddView(PageTurner, 10dip, 10dip, pContent.Width - 20dip, pContent.Height - 20dip)

	If pContent.Width > pContent.Height Then
		PageTurner.TwoPages = True ' the default is False
		PageTurner.RenderLeftPage = True ' the default is True
		Pager.SetPageParameters(Pager.ALIGN_NORMAL, PageTurner.Width/2 - 12dip, LeftMargin, PageTurner.Height - 12dip, TopMargin, LineSpacing)
		PageTurner.SetMarginPixels(6dip, 6dip, 6dip, 6dip)
	Else	
		PageTurner.TwoPages = False 
		PageTurner.RenderLeftPage = False
		Pager.SetPageParameters(Pager.ALIGN_CENTER, PageTurner.Width - 20dip, LeftMargin, PageTurner.Height - 20dip, TopMargin, LineSpacing)
		PageTurner.SetMarginPixels(10dip, 10dip, 10dip, 10dip)
	End If
	PageTurner.AllowLastPageCurl = False ' the default is true	
	
	Font = Font.CreateNew(Typeface.DEFAULT, Typeface.STYLE_BOLD)
	Dim text As String
	For i = 0 To paracount - 1
		text = text & paragraph & CRLF & CRLF & CRLF & CRLF 
		text = text & paragraph2 & CRLF & CRLF & CRLF & CRLF & "(MUTIARA 1) " & CRLF & CRLF & CRLF & CRLF 
		text = text & paragraph3   & CRLF & CRLF   & "(MUTIARA 2) " & CRLF & CRLF & CRLF & CRLF 
		text = text & paragraph4 & CRLF & CRLF & CRLF  & "(MUTIARA 3) " & CRLF & CRLF & CRLF & CRLF
		text = text & paragraph5 & CRLF & CRLF & CRLF & CRLF  & "(MUTIARA 4) " & CRLF & CRLF & CRLF & CRLF & CRLF & CRLF & CRLF & CRLF & CRLF & CRLF 
		text = text & paragraph6 & CRLF & CRLF & CRLF & CRLF   & "(MUTIARA 5) "& CRLF & CRLF & CRLF & CRLF & CRLF & CRLF 
		text = text & paragraph7 & CRLF & CRLF & CRLF & CRLF   & "(MUTIARA 6) "& CRLF & CRLF & CRLF & CRLF  & CRLF & CRLF  
		text = text & paragraph8 & CRLF   & "(MUTIARA 7) "& CRLF & CRLF
		text = text & paragraph9 & CRLF & CRLF    & "(MUTIARA 8) "& CRLF & CRLF & CRLF & CRLF & CRLF & CRLF & CRLF 
		text = text & paragraph10 & CRLF & CRLF    & "(MUTIARA 9) "& CRLF & CRLF & CRLF & CRLF & CRLF & CRLF & CRLF & CRLF & CRLF
		text = text & paragraph11 & CRLF & CRLF    & "(MUTIARA 10) "& CRLF & CRLF & CRLF & CRLF & CRLF
	Next
	Pager.SetPaintParameters(Font, 20, Colors.Black, True)
	Pager.Paginate(text)
	
End Sub

Sub Activity_Resume
	PageTurner.CurrentPage = PageNumber
	PageTurner.Color = Colors.LightGray ' otherwise it gets lost on Pause and Resume without a Create
	PageTurner.OnResume
	
	MediaPlayer1.Play
	timer1.Enabled = True
	timer1_Tick 'don't wait one second for the UI to update.
	MediaPlayer1.Looping = True
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	PageNumber = PageTurner.CurrentPage
	PageTurner.OnPause
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



Sub PageTurner_GetBitmap(Width As Int, Height As Int, Page As Int) As Bitmap 
	Dim bmp As Bitmap
	Dim cnv As Canvas
	bmp.InitializeMutable(Width, Height) 
	Try	
		
		If Page = 0 Then	
			cnv.Initialize2(bmp)
			cnv.DrawColor(Colors.DarkGray)
			Bitmap1.Initialize(File.DirAssets, "3.jpg") 
			DestRect.Initialize(10dip, 130dip, 10dip + 300dip, 10dip + 400dip)
			cnv.DrawBitmap(Bitmap1, Null, DestRect) 
			cnv.DrawText( "(10 Kata-Kata Mutiara TGNA) ", Width/2, 100dip, Typeface.DEFAULT, 24, Colors.White, "CENTER")
			Return bmp
		Else If Page = Pager.PageCount + 1 Then	
			cnv.Initialize2(bmp)
			cnv.DrawColor(Colors.DarkGray)
			cnv.DrawText( "صدق الله العظيم", Width/2, 100dip, Typeface.DEFAULT, 24, Colors.White, "CENTER")
			Return bmp
		Else
			Return Pager.GetPageBitmap(Page - 1, Colors.White)
		End If	
	Catch
		
		PTException
	End Try	
	Return bmp 
End Sub 

Sub PageTurner_GetPages() As Int 
	Try
		Return Pager.PageCount + 2
	Catch
		Return 0 
	End Try 
End Sub

Sub PTException()
	Dim Ex As ExceptionEx
	Dim where As String
	Ex = LastException
	Dim args(2) As Object
	args(0) = LastException.Message
	where = Ex.StackTraceElement(2) 
	args(1) = where	
	PageTurner.RunOnGuiThread("ShowPTError", args)
End Sub




Sub ActionBar_NavigationItemClick
   Activity.Finish
End Sub