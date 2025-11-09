# Build script for Advanced OOP Payroll System
# Usage: .\build.ps1 [clean|build|run-migration|run-app]

param(
    [string]$Action = "build"
)

$ErrorActionPreference = "Stop"

function Clean-Project {
    Write-Host "Cleaning build directory..." -ForegroundColor Yellow
    if (Test-Path "build") {
        Remove-Item -Path "build" -Recurse -Force
    }
    if (Test-Path "sources.txt") {
        Remove-Item -Path "sources.txt" -Force
    }
    Write-Host "✓ Clean complete" -ForegroundColor Green
}

function Build-Project {
    Write-Host "Building Java project..." -ForegroundColor Yellow
    
    # Create build directory
    if (!(Test-Path "build/classes")) {
        New-Item -ItemType Directory -Force -Path "build/classes" | Out-Null
    }
    
    # Find all Java source files
    Write-Host "  Finding source files..."
    $sourceFiles = Get-ChildItem -Path "src" -Recurse -Filter "*.java" | 
        Select-Object -ExpandProperty FullName
    
    $sourceFiles | Out-File -Encoding UTF8 "sources.txt"
    $fileCount = $sourceFiles.Count
    Write-Host "  Found $fileCount Java files"
    
    # Compile
    Write-Host "  Compiling..."
    javac -encoding UTF-8 -cp "lib/*" -d "build/classes" -sourcepath "src" "@sources.txt"
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ Build successful" -ForegroundColor Green
        return $true
    } else {
        Write-Host "✗ Build failed" -ForegroundColor Red
        return $false
    }
}

function Run-Migration {
    Write-Host "Running password migration..." -ForegroundColor Yellow
    java -cp "build/classes;lib/*" MigratePasswordsToBcrypt
}

function Run-App {
    Write-Host "Running main application..." -ForegroundColor Yellow
    java -cp "build/classes;lib/*" Main
}

# Main execution
switch ($Action.ToLower()) {
    "clean" {
        Clean-Project
    }
    "build" {
        Build-Project
    }
    "rebuild" {
        Clean-Project
        Build-Project
    }
    "run-migration" {
        if (Build-Project) {
            Run-Migration
        }
    }
    "run-app" {
        if (Build-Project) {
            Run-App
        }
    }
    "migration" {
        if (Build-Project) {
            Run-Migration
        }
    }
    default {
        Write-Host "Unknown action: $Action" -ForegroundColor Red
        Write-Host ""
        Write-Host "Usage: .\build.ps1 [action]"
        Write-Host ""
        Write-Host "Actions:"
        Write-Host "  clean          - Clean build directory"
        Write-Host "  build          - Build project (default)"
        Write-Host "  rebuild        - Clean and build"
        Write-Host "  run-migration  - Build and run password migration"
        Write-Host "  run-app        - Build and run main application"
    }
}
