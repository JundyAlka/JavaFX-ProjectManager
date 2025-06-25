@echo off
echo ===================================
echo  Menyiapkan Aplikasi Akademik
echo ===================================
echo.

echo [1/3] Membersihkan folder bin...
if exist bin\*.* (
    del /s /q bin\*.*
    rmdir /s /q bin
)
mkdir bin
mkdir bin\view

echo [2/3] Menyalin file FXML...
xcopy /Y /I "src\view\*.fxml" "bin\view\"

echo [3/3] Mengkompilasi kode...
javac --enable-preview --release 23 --module-path "d:\Software After RAR\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml -d bin src\*.java src\model\*.java src\controller\*.java src\util\*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Gagal mengkompilasi!
    pause
    exit /b
)

echo [4/4] Menjalankan aplikasi...
java --enable-preview --module-path "d:\Software After RAR\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp "bin;lib/mysql-connector-j-9.3.0.jar" MainApp

echo.
echo Aplikasi ditutup.
pause