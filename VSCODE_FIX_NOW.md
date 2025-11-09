# 🚨 VS Code BCrypt Fix - Step by Step

## Current Error
```
Exception in thread "AWT-EventQueue-0" java.lang.Error: Unresolved compilation problem: 
BCrypt cannot be resolved
```

## What I Just Did
✅ Added `jbcrypt-0.4.jar` to `.classpath`  
✅ Updated `.vscode/settings.json` with library references  
✅ Cleaned `bin/` and `.settings/` folders  
✅ Verified `lib/jbcrypt-0.4.jar` exists  

## 🔴 ACTION REQUIRED - Do This Now:

### Method 1: Clean Java Workspace (RECOMMENDED)

1. **Open Command Palette:**
   - Press `Ctrl+Shift+P`

2. **Type and select:**
   ```
   Java: Clean Java Language Server Workspace
   ```

3. **Click:**
   ```
   Restart and delete
   ```

4. **Wait** for VS Code to restart (30-60 seconds)

5. **Look for** "Building workspace" in bottom right

6. **Once done**, run Main.java again

---

### Method 2: Manual Reload (If Method 1 doesn't work)

1. **Close VS Code completely** (not just the window, quit the app)

2. **Delete these folders manually** (Windows Explorer):
   - `bin/` folder
   - `.settings/` folder (if exists)
   - `.vscode/.cache/` folder (if exists)

3. **Reopen VS Code**

4. **Wait** for "Building workspace" to complete

5. **Run Main.java**

---

### Method 3: Command Line Build (Bypass VS Code)

If VS Code still won't work, run directly from PowerShell:

```powershell
# Navigate to project
cd C:\Users\zyra\Documents\GitHub\Advanced-OOP-Payroll-System

# Clean
Remove-Item -Recurse -Force bin -ErrorAction SilentlyContinue

# Create output directory
New-Item -ItemType Directory -Force bin

# Find all Java files (excluding tests)
Get-ChildItem -Path src -Recurse -Filter *.java | 
    Where-Object { $_.FullName -notlike '*\test\*' } | 
    ForEach-Object { $_.FullName } | 
    Out-File -Encoding UTF8 sources.txt

# Compile with jBCrypt in classpath
javac -encoding UTF-8 -cp "lib/*" -d bin @sources.txt

# Run the application
java -cp "bin;lib/*" Main
```

---

## How to Verify It's Fixed

After VS Code rebuilds, check:

1. **No red underlines** in `PasswordUtil.java`
2. **No errors** in Problems panel (Ctrl+Shift+M)
3. **Application runs** without BCrypt errors

## Test Login

Once running, use:
- **Username:** `agudel`
- **Password:** `ChangeMe2025!`

---

## Why This Keeps Happening

VS Code's Java Language Server caches the classpath. When you add a new library:
1. `.classpath` file must be updated ✅ (I did this)
2. VS Code must **reload the project** 🔴 (You need to do this)

Without step 2, VS Code uses the OLD cached classpath without jBCrypt.

---

## Nuclear Option (If Nothing Else Works)

Delete the entire Java workspace cache:

**Windows:**
```powershell
Remove-Item -Recurse -Force "$env:APPDATA\Code\User\workspaceStorage" -ErrorAction SilentlyContinue
```

Then restart VS Code and reopen project.

---

## Alternative: Use NetBeans Instead

If VS Code continues to have issues, the project works perfectly in NetBeans:
1. Open in NetBeans
2. Clean and Build (Shift+F11)
3. Run

The jBCrypt library is already configured in `nbproject/project.properties`.

---

## Next Steps After Login Works

Once you successfully login with `ChangeMe2025!`, you should:
1. ✅ Test with multiple users
2. ✅ Implement password change on first login
3. ✅ Add password strength requirements
4. ✅ Update user documentation

---

## Need Help?

If still not working after Method 1 and Method 2:
1. Check Java extension is installed (Extension Pack for Java)
2. Check Java version: `java -version` (should be 21)
3. Try Method 3 (command line)
4. Consider using NetBeans for this project
