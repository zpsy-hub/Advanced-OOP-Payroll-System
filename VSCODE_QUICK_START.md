# VS Code Quick Start - Password Migration

## 🚀 One-Command Setup (In VS Code Terminal)

Open VS Code integrated terminal (Ctrl+` or View → Terminal) and run:

```powershell
# Build and run migration in one command
.\build.ps1 run-migration
```

That's it! This will:
1. ✅ Compile all Java files
2. ✅ Run the password migration
3. ✅ Convert all passwords to BCrypt
4. ✅ Set temp password: `ChangeMe2025!`

---

## 📋 Available Commands

All commands in PowerShell terminal:

```powershell
# Build only
.\build.ps1 build

# Clean and rebuild
.\build.ps1 rebuild

# Build and run password migration
.\build.ps1 run-migration

# Build and run main app
.\build.ps1 run-app

# Clean build directory
.\build.ps1 clean
```

---

## 🎯 Alternative: Use VS Code Tasks

### Method 1: Command Palette (Ctrl+Shift+P)
1. Press `Ctrl+Shift+P`
2. Type "Tasks: Run Task"
3. Select one of:
   - **Clean and Build** - Rebuild everything
   - **Run Password Migration** - Run the BCrypt migration
   - **Run Main Application** - Launch the app

### Method 2: Terminal Menu
1. Menu: **Terminal** → **Run Task...**
2. Pick a task from the list

### Method 3: Keyboard Shortcut
1. Press `Ctrl+Shift+B` (default build)
2. This runs "Build Java Project"

---

## 🏃 Step-by-Step Migration (VS Code)

### Step 1: Open Integrated Terminal
- Press `` Ctrl+` `` (backtick) or
- Menu: **View** → **Terminal**

### Step 2: Run Migration
```powershell
.\build.ps1 run-migration
```

**Expected output:**
```
Building Java project...
  Finding source files...
  Found XX Java files
  Compiling...
✓ Build successful
Running password migration...
============================================================
Password Migration: SHA-256 → BCrypt
============================================================

[1/4] Creating backup table...
✓ Backup created: user_backup_sha256

[2/4] Counting users...
✓ Found X users to migrate

[3/4] Generating BCrypt hash...
✓ Temporary password: ChangeMe2025!

[4/4] Updating passwords...
✓ Updated X passwords

[5/5] Verifying migration...
  ✓ All passwords successfully migrated to BCrypt!

✓ MIGRATION COMPLETE!
```

### Step 3: Test Login
```powershell
# Run the application
.\build.ps1 run-app
```

Login with:
- **Username:** any existing username
- **Password:** `ChangeMe2025!`

---

## 🔧 Troubleshooting

### "javac is not recognized"
**Fix:** Install JDK and add to PATH

Check JDK:
```powershell
java -version
javac -version
```

If missing, install JDK 21 and add to PATH:
```powershell
# Example (adjust path to your JDK install)
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.0.0-hotspot"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path
```

### "Cannot connect to database"
**Fix:** Start MySQL

```powershell
# Check if MySQL is running
Test-NetConnection -ComputerName 127.0.0.1 -Port 3306

# Start MySQL service
Start-Service -Name MySQL80  # or your MySQL service name

# OR use Docker
docker start mysql-payroll
```

### Build fails with package errors
**Fix:** Check `lib/jbcrypt-0.4.jar` exists

```powershell
# Verify jar exists
Test-Path .\lib\jbcrypt-0.4.jar

# If missing, download it
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar" -OutFile ".\lib\jbcrypt-0.4.jar"
```

---

## 🎨 VS Code Java Extensions (Optional)

For better Java support in VS Code, install:

1. **Extension Pack for Java** (Microsoft)
   - Press `Ctrl+Shift+X`
   - Search "Extension Pack for Java"
   - Click Install

This gives you:
- IntelliSense
- Debugging
- Test runner
- Better build integration

---

## 🔍 Verify Migration Success

### In VS Code Terminal:
```powershell
# Check database (if you have mysql CLI)
mysql -h 127.0.0.1 -P 3306 -u root -p

# Then in MySQL prompt:
USE payrollsystem_db;
SELECT username, LEFT(password, 20) AS hash_prefix FROM user LIMIT 5;
# Should see: $2a$12$... (BCrypt hashes)

# Check backup exists
SHOW TABLES LIKE 'user_backup%';
```

### Or use VS Code Database Extension:
1. Install "MySQL" extension
2. Connect to your database
3. Browse tables and verify

---

## ✅ Success Checklist

- [ ] Build completes without errors (`.\build.ps1 build`)
- [ ] Migration runs successfully (`.\build.ps1 run-migration`)
- [ ] Console shows "MIGRATION COMPLETE!"
- [ ] Can login with password `ChangeMe2025!`
- [ ] Database has BCrypt hashes (start with `$2a$`)

---

## 📁 What's in This Workspace

**VS Code Config:**
- `.vscode/tasks.json` - Build & run tasks
- `.vscode/launch.json` - Debug configurations

**Build Scripts:**
- `build.ps1` - Main build script (use this!)

**Migration Files:**
- `src/MigratePasswordsToBcrypt.java` - Java migration utility
- `src/GeneratePasswordHash.java` - Hash generator
- `src/util/PasswordUtil.java` - BCrypt utility class

**Documentation:**
- `VSCODE_QUICK_START.md` - This file
- `QUICK_START_MIGRATION.md` - NetBeans version
- `PASSWORD_MIGRATION_GUIDE.md` - Full documentation

---

## 🚀 Quick Reference

```powershell
# Most common workflow:

# 1. Build and migrate passwords
.\build.ps1 run-migration

# 2. Run the app
.\build.ps1 run-app

# Login with: ChangeMe2025!
```

---

## 🎯 Next Steps After Migration

1. **Test login thoroughly** - Try multiple users
2. **Implement password change UI** - Force users to change from temp password
3. **Add password complexity rules** - Minimum length, special chars, etc.
4. **Enable account lockout** - Prevent brute-force attacks
5. **Set up HTTPS** - Never send passwords over plain HTTP

---

Need help? Check:
- `PASSWORD_MIGRATION_GUIDE.md` for full docs
- VS Code Output panel for build errors
- Terminal output for runtime errors
