# BCrypt Password Migration - Quick Start Guide

## ✅ What's Already Done

- [x] BCrypt library (`jbcrypt-0.4.jar`) downloaded to `lib/` folder
- [x] Project properties updated to include jbcrypt in classpath
- [x] `PasswordUtil.java` created with secure BCrypt hashing
- [x] `LoginDAO.java` updated to use BCrypt
- [x] `CredentialsManagementDAO.java` updated to use BCrypt
- [x] Migration utility `MigratePasswordsToBcrypt.java` created
- [x] Hash generator `GeneratePasswordHash.java` created

## 🚀 Steps to Complete (In NetBeans)

### Step 1: Build the Project
1. **Open NetBeans** and load this project
2. Right-click on project name → **Clean and Build** (or press Shift+F11)
3. Wait for build to complete
4. Check the Output window for any errors

**Expected result:** Build should succeed with message "BUILD SUCCESSFUL"

**If build fails with "cannot resolve org.mindrot.jbcrypt":**
- Go to Project Properties → Libraries → Compile tab
- Click "Add JAR/Folder"
- Browse to `lib/jbcrypt-0.4.jar` and add it
- Try Clean and Build again

---

### Step 2: Run Password Migration
1. In NetBeans **Projects** panel, expand `Source Packages`
2. Find `MigratePasswordsToBcrypt.java` (in the `<default package>`)
3. Right-click → **Run File** (or press Shift+F6)

**Expected console output:**
```
============================================================
Password Migration: SHA-256 → BCrypt
============================================================

[1/4] Creating backup table...
✓ Backup created: user_backup_sha256

[2/4] Counting users...
✓ Found X users to migrate

[3/4] Generating BCrypt hash for temporary password...
✓ Temporary password: ChangeMe2025!
✓ BCrypt hash: $2a$12$LQv3c1yqBw1L...

[4/4] Updating passwords to BCrypt...
✓ Updated X passwords

[5/5] Verifying migration...
  Total users:     X
  BCrypt hashes:   X
  SHA-256 hashes:  0
  ✓ All passwords successfully migrated to BCrypt!

============================================================
✓ MIGRATION COMPLETE!
============================================================

All users can now login with:
  Password: ChangeMe2025!
```

**If you see errors:**
- Check that MySQL is running (see Step 3 below)
- Check that `config.properties` has correct DB credentials
- Verify database `payrollsystem_db` exists

---

### Step 3: Verify MySQL is Running

**Option A: Check with PowerShell**
```powershell
Test-NetConnection -ComputerName 127.0.0.1 -Port 3306
```
Should show: `TcpTestSucceeded : True`

**Option B: Start MySQL Service (if stopped)**
```powershell
# Find MySQL service name
Get-Service -Name *mysql*

# Start it (replace MySQL80 with your service name)
Start-Service -Name MySQL80
```

**Option C: Use Docker MySQL (if you set it up earlier)**
```powershell
# Start existing container
docker start mysql-payroll

# Or create new one
docker run --name mysql-payroll -e MYSQL_ROOT_PASSWORD='DF.w}=;$CLn+84?m]r(M%Q' -e MYSQL_DATABASE=payrollsystem_db -p 3306:3306 -d mysql:8.0
```

---

### Step 4: Test Login

1. In NetBeans, find and run your main application (e.g., `Main.java` or `GUIlogin.java`)
2. At the login screen, try any existing username with password: **`ChangeMe2025!`**
3. Login should succeed!

**Test usernames** (check your database for actual usernames):
- Common examples: `admin`, `employee1`, `hr_user`, etc.
- All should work with password: `ChangeMe2025!`

---

### Step 5: (Optional) Create Test Users with Custom Passwords

If you want specific test users with known passwords:

1. Run `GeneratePasswordHash.java`:
   - Right-click → **Run File**
   - This generates BCrypt hashes for common test passwords

2. Copy a hash from the output

3. Update database (use NetBeans Services tab → Databases, or MySQL Workbench):
   ```sql
   UPDATE payrollsystem_db.user 
   SET password = '$2a$12$your_copied_hash_here' 
   WHERE username = 'your_username';
   ```

---

## 🔍 Troubleshooting

### Build Fails: "cannot resolve org.mindrot.jbcrypt.BCrypt"
**Solution:**
1. Verify `lib/jbcrypt-0.4.jar` exists
2. Project Properties → Libraries → Add JAR/Folder → select jbcrypt jar
3. Clean and Build

### Migration Fails: "Database connection is null"
**Solution:**
1. Check MySQL is running: `Test-NetConnection -ComputerName 127.0.0.1 -Port 3306`
2. Check `config.properties`:
   ```properties
   db.host=127.0.0.1
   db.port=3306
   db.name=payrollsystem_db
   db.user=root
   db.password=your_password
   ```
3. Import database: `src/mysql/working AOOP.sql`

### Login Fails After Migration
**Solution:**
1. Verify migration ran successfully (check console output)
2. Check database passwords start with `$2a$`:
   ```sql
   SELECT username, LEFT(password, 20) FROM payrollsystem_db.user LIMIT 5;
   ```
3. Use exactly: `ChangeMe2025!` (case-sensitive, with exclamation mark)

---

## 📊 Verify Database Changes

Run these SQL queries to verify migration:

```sql
-- Check backup was created
SHOW TABLES LIKE 'user_backup_sha256';

-- Compare old vs new passwords
SELECT 'OLD (SHA-256)' AS type, LEFT(password, 20) AS hash_sample 
FROM user_backup_sha256 
LIMIT 1
UNION ALL
SELECT 'NEW (BCrypt)', LEFT(password, 20) 
FROM user 
LIMIT 1;

-- Count password types
SELECT 
  COUNT(*) AS total,
  SUM(CASE WHEN password LIKE '$2a$%' THEN 1 ELSE 0 END) AS bcrypt_count
FROM user;
```

---

## ✅ Success Checklist

- [ ] Project builds successfully in NetBeans
- [ ] `MigratePasswordsToBcrypt` runs without errors
- [ ] Console shows "MIGRATION COMPLETE!"
- [ ] Database backup table `user_backup_sha256` exists
- [ ] All passwords in `user` table start with `$2a$` or `$2b$`
- [ ] Can login with any username and password `ChangeMe2025!`
- [ ] Password change functionality works (stores new BCrypt hash)

---

## 🎯 What You Just Fixed

**Before:** 
- ❌ SHA-256 with no salt (vulnerable to rainbow tables)
- ❌ Same password = same hash
- ❌ Fast brute-force (~billions/second on GPU)

**After:**
- ✅ BCrypt with auto-generated unique salt per password
- ✅ Adaptive cost factor (resistant to brute-force)
- ✅ Industry standard (OWASP recommended)
- ✅ ~300ms per hash = max 3 login attempts/second

---

## 📞 Need Help?

If something doesn't work:
1. Check Output window in NetBeans for error messages
2. Verify all files in checklist exist
3. Ensure MySQL is running and database imported
4. Check `config.properties` has correct credentials

**Files created:**
- `src/util/PasswordUtil.java` - BCrypt utility
- `src/MigratePasswordsToBcrypt.java` - Migration runner
- `src/GeneratePasswordHash.java` - Hash generator
- `lib/jbcrypt-0.4.jar` - BCrypt library
- `PASSWORD_MIGRATION_GUIDE.md` - Full documentation
- `src/mysql/migrate_passwords_to_bcrypt.sql` - SQL version (if you prefer SQL client)
