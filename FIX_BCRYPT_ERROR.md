# 🔧 Fix BCrypt Error in NetBeans

## The Problem
- ✅ Login works perfectly in command line
- ✅ BCrypt library is properly configured
- ❌ BCrypt error appears when running through NetBeans GUI

## Root Cause
NetBeans has cached the old build without the BCrypt library. The `build/` folder needs to be cleaned.

## Solution (Choose ONE)

### Option 1: Clean and Build in NetBeans (Recommended)
1. **Right-click** on the project in NetBeans
2. Click **"Clean"** (this deletes the build folder)
3. Click **"Clean and Build"** (or press **Shift+F11**)
4. **Run** the project again

### Option 2: Manual Clean (if NetBeans won't work)
1. **Close NetBeans** completely
2. **Delete these folders** in your project directory:
   - `build/`
   - `dist/`
3. **Reopen NetBeans**
4. **Clean and Build** the project (Shift+F11)
5. **Run** the project

### Option 3: Command Line Build
```powershell
# Navigate to project directory
cd C:\Users\zyra\Documents\GitHub\Advanced-OOP-Payroll-System

# Clean
Remove-Item -Recurse -Force build, dist -ErrorAction SilentlyContinue

# Build with Ant (NetBeans' build tool)
ant clean
ant compile
ant run
```

## Verify the Fix

After cleaning and rebuilding, test login with:
- **Username:** `agudel` (or any username from the database)
- **Password:** `ChangeMe2025!`

## If Still Not Working

Run this diagnostic from project root:
```powershell
# Check if jbcrypt is in the built JAR
jar tf dist/Advanced-OOP-Payroll-System.jar | Select-String "jbcrypt"

# Or check the manifest
jar xf dist/Advanced-OOP-Payroll-System.jar META-INF/MANIFEST.MF
cat META-INF/MANIFEST.MF
```

The Class-Path in MANIFEST.MF should include `lib/jbcrypt-0.4.jar`.

## Why This Happens

When you added `jbcrypt-0.4.jar` to the project:
1. ✅ It was added to `project.properties` (compile-time classpath)
2. ✅ It was added to the `lib/` folder
3. ❌ BUT the existing `build/` folder still had old compiled classes
4. ❌ NetBeans kept using the old build without the library

**Cleaning forces NetBeans to rebuild from scratch with the new library.**

## Quick Test Commands

After rebuilding, verify BCrypt works:
```powershell
# From project root
java -cp "build/classes;lib/*" SimpleBCryptTest
java -cp "build/classes;lib/*" DebugLogin agudel "ChangeMe2025!"
```

Both should show ✓ SUCCESS with no errors.

## Login Credentials

After the fix, use these to login:

**Password for ALL users:** `ChangeMe2025!`

**Sample usernames:**
- agudel
- alvrod
- atiros
- baumar
- casjoh

---

## Summary

The BCrypt security upgrade is complete and working. You just need to:

1. **Clean and Build** in NetBeans
2. **Run** the application
3. **Login** with any username and password `ChangeMe2025!`

That's it! 🎉
