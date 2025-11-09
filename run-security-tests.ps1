# Security Controls Test Runner
# Compiles and executes TestNG tests for Controls 2 & 4

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Security Controls Test Suite" -ForegroundColor Cyan
Write-Host "  Controls 2 & 4 Unit Tests" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Configuration
$projectRoot = $PSScriptRoot
$srcDir = Join-Path $projectRoot "src"
$binDir = Join-Path $projectRoot "bin"
$libDir = Join-Path $projectRoot "lib"
$testOutputDir = Join-Path $projectRoot "test-output"

# Check prerequisites
Write-Host "Checking prerequisites..." -ForegroundColor Yellow

if (-not (Test-Path $srcDir)) {
    Write-Host "ERROR: src directory not found!" -ForegroundColor Red
    exit 1
}

if (-not (Test-Path $libDir)) {
    Write-Host "ERROR: lib directory not found!" -ForegroundColor Red
    exit 1
}

# Check for TestNG jar
$testngJar = Get-ChildItem -Path $libDir -Filter "testng*.jar" -ErrorAction SilentlyContinue
if (-not $testngJar) {
    Write-Host "WARNING: TestNG jar not found in lib/" -ForegroundColor Yellow
    Write-Host "Please download TestNG 7.4.0+ from https://testng.org/" -ForegroundColor Yellow
    Write-Host "Place testng-7.4.0.jar in the lib/ directory" -ForegroundColor Yellow
    $continue = Read-Host "Continue anyway? (y/n)"
    if ($continue -ne 'y') {
        exit 1
    }
}

Write-Host "Prerequisites OK" -ForegroundColor Green
Write-Host ""

# Create bin directory if it doesn't exist
if (-not (Test-Path $binDir)) {
    Write-Host "Creating bin directory..." -ForegroundColor Yellow
    New-Item -ItemType Directory -Path $binDir | Out-Null
}

# Step 1: Compile all source files
Write-Host "Step 1: Compiling source files..." -ForegroundColor Yellow

$classpath = "$libDir\*;$binDir"
$javaFiles = Get-ChildItem -Path $srcDir -Filter "*.java" -Recurse

Write-Host "Found $($javaFiles.Count) Java files" -ForegroundColor Gray

try {
    javac -d $binDir -cp $classpath -encoding UTF-8 (Get-ChildItem -Path $srcDir -Recurse -Filter "*.java" | ForEach-Object { $_.FullName })
    
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Compilation failed with errors!" -ForegroundColor Red
        Write-Host "Please fix compilation errors and try again." -ForegroundColor Red
        exit 1
    }
    
    Write-Host "Compilation successful!" -ForegroundColor Green
} catch {
    Write-Host "ERROR during compilation: $_" -ForegroundColor Red
    exit 1
}

Write-Host ""

# Step 2: Run TestNG suite
Write-Host "Step 2: Running TestNG test suite..." -ForegroundColor Yellow
Write-Host ""

$testngXml = Join-Path $projectRoot "testng.xml"

if (-not (Test-Path $testngXml)) {
    Write-Host "ERROR: testng.xml not found!" -ForegroundColor Red
    exit 1
}

Write-Host "Test Suite: testng.xml" -ForegroundColor Gray
Write-Host "Running tests..." -ForegroundColor Gray
Write-Host ""

try {
    $classpath = "$binDir;$libDir\*"
    
    # Run TestNG
    java -cp $classpath org.testng.TestNG $testngXml
    
    $exitCode = $LASTEXITCODE
    
    Write-Host ""
    
    if ($exitCode -eq 0) {
        Write-Host "========================================" -ForegroundColor Green
        Write-Host "  All Tests Passed!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Green
    } else {
        Write-Host "========================================" -ForegroundColor Yellow
        Write-Host "  Some Tests Failed (Exit Code: $exitCode)" -ForegroundColor Yellow
        Write-Host "========================================" -ForegroundColor Yellow
    }
    
} catch {
    Write-Host "ERROR running tests: $_" -ForegroundColor Red
    exit 1
}

Write-Host ""

# Step 3: Show test results summary
Write-Host "Step 3: Test Results Summary" -ForegroundColor Yellow
Write-Host ""

if (Test-Path $testOutputDir) {
    $reportHtml = Join-Path $testOutputDir "index.html"
    $emailableReport = Join-Path $testOutputDir "emailable-report.html"
    $xmlResults = Join-Path $testOutputDir "testng-results.xml"
    
    if (Test-Path $xmlResults) {
        [xml]$results = Get-Content $xmlResults
        
        $totalTests = $results.testng.'test-results'.total
        $passed = $results.testng.'test-results'.passed
        $failed = $results.testng.'test-results'.failed
        $skipped = $results.testng.'test-results'.skipped
        
        Write-Host "Total Tests:   $totalTests" -ForegroundColor Cyan
        Write-Host "Passed:        $passed" -ForegroundColor Green
        Write-Host "Failed:        $failed" -ForegroundColor $(if ($failed -eq 0) { "Gray" } else { "Red" })
        Write-Host "Skipped:       $skipped" -ForegroundColor Yellow
        Write-Host ""
        
        $successRate = [math]::Round(($passed / $totalTests) * 100, 2)
        Write-Host "Success Rate:  $successRate%" -ForegroundColor $(if ($successRate -ge 90) { "Green" } elseif ($successRate -ge 70) { "Yellow" } else { "Red" })
    }
    
    Write-Host ""
    Write-Host "Test reports generated:" -ForegroundColor Gray
    Write-Host "  - $reportHtml" -ForegroundColor Gray
    Write-Host "  - $emailableReport" -ForegroundColor Gray
    Write-Host ""
    
    # Ask to open report
    $openReport = Read-Host "Open test report in browser? (y/n)"
    if ($openReport -eq 'y') {
        if (Test-Path $reportHtml) {
            Start-Process $reportHtml
            Write-Host "Report opened in browser" -ForegroundColor Green
        } else {
            Write-Host "Report file not found" -ForegroundColor Red
        }
    }
} else {
    Write-Host "WARNING: test-output directory not found" -ForegroundColor Yellow
    Write-Host "Tests may not have executed properly" -ForegroundColor Yellow
}

Write-Host ""

# Step 4: Check log files
Write-Host "Step 4: Checking log files..." -ForegroundColor Yellow

$logsDir = Join-Path $projectRoot "logs"

if (Test-Path $logsDir) {
    $errorLog = Join-Path $logsDir "error.log"
    $appLog = Join-Path $logsDir "application.log"
    
    Write-Host "Log files generated:" -ForegroundColor Gray
    
    if (Test-Path $errorLog) {
        $errorSize = (Get-Item $errorLog).Length
        Write-Host "  - error.log ($([math]::Round($errorSize/1KB, 2)) KB)" -ForegroundColor Gray
    }
    
    if (Test-Path $appLog) {
        $appSize = (Get-Item $appLog).Length
        Write-Host "  - application.log ($([math]::Round($appSize/1KB, 2)) KB)" -ForegroundColor Gray
    }
    
    Write-Host ""
    
    $viewLogs = Read-Host "View last 20 log entries? (y/n)"
    if ($viewLogs -eq 'y') {
        if (Test-Path $errorLog) {
            Write-Host ""
            Write-Host "=== Last 20 entries from error.log ===" -ForegroundColor Cyan
            Get-Content $errorLog -Tail 20
        }
        
        if (Test-Path $appLog) {
            Write-Host ""
            Write-Host "=== Last 20 entries from application.log ===" -ForegroundColor Cyan
            Get-Content $appLog -Tail 20
        }
    }
} else {
    Write-Host "No logs directory found (tests may not have generated logs)" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Test Execution Complete" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Step 5: Next steps
Write-Host "Next Steps:" -ForegroundColor Yellow
Write-Host "  1. Review test results in test-output/index.html" -ForegroundColor Gray
Write-Host "  2. Execute manual tests (see SECURITY_TESTS_GUIDE.md)" -ForegroundColor Gray
Write-Host "  3. Inspect log files for proper formatting" -ForegroundColor Gray
Write-Host "  4. Document test results for milestone submission" -ForegroundColor Gray
Write-Host ""

Write-Host "Press any key to exit..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
