# PowerShell script to replace printStackTrace() with SecureLogger calls
# This ensures secure error handling across the entire codebase

$rootPath = "src"
$targetFiles = @(
    # DAOs (critical - database operations)
    "DAO\CredentialsManagementDAO.java",
    "DAO\EmployeeDAO.java",
    "DAO\LeaveBalanceDAO.java",
    "DAO\LeaveDAO.java",
    "DAO\LeaveRequestLogDAO.java",
    "DAO\LogsDAO.java",
    "DAO\MonthlySummaryReportDAO.java",
    "DAO\OvertimeDAO.java",
    "DAO\PayslipDAO.java",
    "DAO\PermissionDAO.java",
    "DAO\TimesheetDAO.java",
    
    # Services
    "service\EmployeeCSVParser.java",
    
    # Views (GUI - critical for user experience)
    "view\GUIlogin.java",
    "view\GUIDashboard.java",
    "view\GUILeaveRequest.java",
    "view\GUIOvertimeRequest.java",
    "view\GUIPayslip.java",
    "view\GUITimeInOut.java",
    "view\GUI_HRAttendanceManagement.java",
    "view\GUI_HREmployeeManagement.java",
    "view\GUI_HRLeaveManagement.java",
    "view\GUI_HROvertimemanagement.java",
    "view\GUI_ITCredentialsManagement.java",
    "view\GUI_ITLogs.java",
    "view\GUI_ITPermissions.java",
    "view\GUI_PayrollMonthlySummary.java",
    "view\GUI_PayrollSalaryCalculation.java",
    "view\PayslipDialog.java",
    "view\EmployeeDialog.java",
    "customUI\Sidebar.java"
)

$totalReplaced = 0
$filesProcessed = 0

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "Secure Error Handling Implementation" -ForegroundColor Cyan
Write-Host "======================================`n" -ForegroundColor Cyan

foreach ($file in $targetFiles) {
    $filePath = Join-Path $rootPath $file
    
    if (Test-Path $filePath) {
        Write-Host "Processing: $file" -ForegroundColor Yellow
        
        # Read file content
        $content = Get-Content $filePath -Raw
        
        # Check if SecureLogger import exists
        if ($content -notmatch "import util\.SecureLogger;") {
            # Add import after other util imports or before first import
            if ($content -match "(import util\.\w+;)") {
                $content = $content -replace "(import util\.\w+;)", "`$1`nimport util.SecureLogger;"
            } elseif ($content -match "(import \w+\.\w+;)") {
                $content = $content -replace "(package \w+;)", "`$1`nimport util.SecureLogger;"
            }
        }
        
        # Extract class name from file path
        $className = [System.IO.Path]::GetFileNameWithoutExtension($file)
        
        # Count occurrences before replacement
        $beforeCount = ([regex]::Matches($content, "\.printStackTrace\(\)")).Count
        
        # Replace printStackTrace() with SecureLogger calls
        # Pattern: e.printStackTrace(); -> SecureLogger.logError("ClassName.method", e);
        # We'll use a generic context since method name extraction is complex
        $content = $content -replace 'e\.printStackTrace\(\);', "SecureLogger.logError(""$className"", e);"
        $content = $content -replace 'ex\.printStackTrace\(\);', "SecureLogger.logError(""$className"", ex);"
        $content = $content -replace 'e1\.printStackTrace\(\);', "SecureLogger.logError(""$className"", e1);"
        
        # Remove comments like "// Handle exceptions appropriately"
        $content = $content -replace '\s*//\s*Handle exceptions appropriately', ''
        $content = $content -replace '\s*//\s*Log the exception to console or a log file', ''
        $content = $content -replace '\s*//\s*Handle rollback error', ''
        $content = $content -replace '\s*//\s*Handle reset auto-commit error', ''
        
        # Count occurrences after replacement
        $afterCount = ([regex]::Matches($content, "\.printStackTrace\(\)")).Count
        $replaced = $beforeCount - $afterCount
        
        if ($replaced -gt 0) {
            # Write updated content back to file
            Set-Content $filePath $content -NoNewline
            Write-Host "  ✓ Replaced $replaced printStackTrace() call(s)" -ForegroundColor Green
            $totalReplaced += $replaced
            $filesProcessed++
        } else {
            Write-Host "  - No printStackTrace() found" -ForegroundColor Gray
        }
    } else {
        Write-Host "  ✗ File not found: $filePath" -ForegroundColor Red
    }
}

Write-Host "`n======================================" -ForegroundColor Cyan
Write-Host "Summary:" -ForegroundColor Cyan
Write-Host "  Files processed: $filesProcessed" -ForegroundColor White
Write-Host "  Total printStackTrace() replaced: $totalReplaced" -ForegroundColor Green
Write-Host "======================================`n" -ForegroundColor Cyan

Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "  1. Review changes with: git diff" -ForegroundColor White
Write-Host "  2. Build project to verify no errors" -ForegroundColor White
Write-Host "  3. Test application functionality" -ForegroundColor White
Write-Host "  4. Commit with: git commit -am 'feat: Implement secure error handling (Control 4)'" -ForegroundColor White
