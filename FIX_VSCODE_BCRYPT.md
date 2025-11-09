# 🔧 Fix BCrypt Error in VS Code

## Problem
```
Exception in thread "AWT-EventQueue-0" java.lang.Error: Unresolved compilation problem: 
BCrypt cannot be resolved
```

## Root Cause
The `.classpath` file was missing the `jbcrypt-0.4.jar` entry, so VS Code's Java extension couldn't find the BCrypt library.

## ✅ FIX APPLIED

I just added `jbcrypt-0.4.jar` to:
1. `.classpath` file
2. `.vscode/settings.json` 

## Next Steps - RELOAD VS CODE

### Option 1: Reload Window (Fastest)
1. Press **Ctrl+Shift+P** (Command Palette)
2. Type: **"Reload Window"**
3. Press Enter
4. Wait for Java extension to reload
5. Run the application

### Option 2: Clean Java Workspace (Recommended)
1. Press **Ctrl+Shift+P** (Command Palette)
2. Type: **"Java: Clean Java Language Server Workspace"**
3. Click **"Restart and delete"**
4. Wait for VS Code to restart and rebuild
5. Run the application

### Option 3: Restart VS Code
1. Close VS Code completely
2. Reopen VS Code
3. Open the project
4. Wait for Java extension to index (bottom right corner)
5. Run the application

## Verify the Fix

After reloading, check the bottom panel in VS Code:
- ✅ Should show: "Build finished" or similar (no errors)
- ❌ If still shows errors, try Option 2 (Clean Java Workspace)

## Test Login

Once running, use these credentials:
- **Username:** `agudel` (or any username from database)
- **Password:** `ChangeMe2025!`

## Still Not Working?

If you still see BCrypt errors after reloading:

1. **Check Java Extension is installed:**
   - Open Extensions (Ctrl+Shift+X)
   - Search for "Extension Pack for Java"
   - Make sure it's installed and enabled

2. **Force rebuild:**
   ```powershell
   # Delete VS Code cache
   Remove-Item -Recurse -Force .vscode/.cache -ErrorAction SilentlyContinue
   
   # Delete build output
   Remove-Item -Recurse -Force bin -ErrorAction SilentlyContinue
   
   # Reload VS Code
   ```

3. **Verify jar file exists:**
   ```powershell
   Test-Path lib/jbcrypt-0.4.jar
   # Should return: True
   ```

## Why This Happened

- ✅ jBCrypt was added to NetBeans project (`nbproject/project.properties`)
- ✅ jBCrypt was added to the `lib/` folder
- ❌ BUT `.classpath` file (used by VS Code/Eclipse) wasn't updated
- ❌ VS Code couldn't find the library during compilation

Now it's fixed! Just reload VS Code and you're good to go. 🚀
