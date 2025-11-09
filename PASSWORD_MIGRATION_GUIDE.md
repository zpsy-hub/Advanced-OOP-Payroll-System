# Password Security Upgrade: SHA-256 → BCrypt

## Overview
This upgrade replaces the **insecure SHA-256 hashing (no salt)** with **BCrypt**, a modern password hashing algorithm that:
- Automatically generates and stores a unique salt per password
- Uses adaptive cost factor (configurable work factor) to remain secure against brute-force
- Follows industry best practices (OWASP recommended)

## Files Changed
- `src/util/PasswordUtil.java` — new utility class for BCrypt hashing/verification
- `src/DAO/LoginDAO.java` — updated to use `PasswordUtil`
- `src/DAO/CredentialsManagementDAO.java` — updated to use `PasswordUtil`

---

## Step 1: Add BCrypt Library (jBCrypt)

### Option A: Download jBCrypt JAR manually
1. Download the latest jBCrypt jar from Maven Central or use this direct link:
   - **Maven Central**: https://repo1.maven.org/maven2/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar
   - Or use PowerShell to download:
     ```powershell
     Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar" -OutFile ".\lib\jbcrypt-0.4.jar"
     ```

2. Place the JAR in your `lib/` folder:
   ```
   lib/
     jbcrypt-0.4.jar
   ```

### Option B: Use Maven/Gradle (if migrating to Maven/Gradle)
Add to `pom.xml`:
```xml
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>
```

Or `build.gradle`:
```gradle
implementation 'org.mindrot:jbcrypt:0.4'
```

---

## Step 2: Update NetBeans Project Classpath

### Using NetBeans GUI:
1. Right-click your project → **Properties**
2. Go to **Libraries** → **Compile** tab
3. Click **Add JAR/Folder**
4. Browse to `lib/jbcrypt-0.4.jar` and add it
5. Click **OK**

### Or manually edit `nbproject/project.properties`:
Add this line to the `javac.classpath` property (after the last jar):
```properties
javac.classpath=\
    ${file.reference.barcodes-8.0.4.jar}:\
    ... (existing jars) ...\
    ${file.reference.jbcrypt-0.4.jar}
```

And add the file reference near the other `file.reference.*` entries:
```properties
file.reference.jbcrypt-0.4.jar=lib/jbcrypt-0.4.jar
```

---

## Step 3: Database Migration — Rehash Existing Passwords

### ⚠️ CRITICAL: Existing user passwords in the database are SHA-256 hashes and CANNOT be verified with BCrypt.

You have **two migration options**:

### Option A: Reset All User Passwords (Recommended for dev/testing)
Force all users to reset passwords on next login.

**SQL Script** (run in MySQL Workbench or CLI):
```sql
USE payrollsystem_db;

-- Backup the user table first (optional but recommended)
CREATE TABLE user_backup_before_bcrypt AS SELECT * FROM user;

-- Set all passwords to a known temporary value (bcrypt hash of "ChangeMe123!")
-- Users will be forced to change on first login
-- BCrypt hash for "ChangeMe123!" with cost 12:
UPDATE user 
SET password = '$2a$12$R7xH7kZ5J3oK9L4N1M2P3OeQrStUvWxYz0AbCdEfGhIjKlMnOpQr6';

-- You can generate your own temporary password hash using this Java snippet:
-- System.out.println(PasswordUtil.hashPassword("ChangeMe123!"));
```

**Application-side logic** (add to `LoginDAO.authenticateUser`):
```java
// After successful authentication, check if password needs reset
if (storedPassword.equals("$2a$12$R7xH7kZ5J3oK9L4N1M2P3OeQrStUvWxYz0AbCdEfGhIjKlMnOpQr6")) {
    // Show password reset dialog
    // Force user to set new password before proceeding
}
```

### Option B: Manual Migration with Known Plaintext Passwords (if you have them)
If you have a list/export of current usernames and plaintext passwords:

1. Create a Java migration utility:
   ```java
   import util.PasswordUtil;
   // ... connect to DB
   PreparedStatement ps = conn.prepareStatement(
       "UPDATE payrollsystem_db.user SET password = ? WHERE username = ?"
   );
   for (User user : usersWithPlaintextPasswords) {
       String bcryptHash = PasswordUtil.hashPassword(user.getPlaintextPassword());
       ps.setString(1, bcryptHash);
       ps.setString(2, user.getUsername());
       ps.executeUpdate();
   }
   ```

2. Or use SQL with known passwords (example for a test user):
   ```sql
   -- Example: if you know user 'admin' has password 'admin123', generate hash first in Java:
   -- String hash = PasswordUtil.hashPassword("admin123");
   -- Then run:
   UPDATE payrollsystem_db.user 
   SET password = '$2a$12$...(bcrypt hash here)...' 
   WHERE username = 'admin';
   ```

---

## Step 4: Test the Migration

### Create a test user or verify login:
```sql
-- Insert a test user with BCrypt password "test123"
-- (generate hash first using Java: PasswordUtil.hashPassword("test123"))
INSERT INTO payrollsystem_db.user (emp_id, username, password)
VALUES (9999, 'testuser', '$2a$12$exampleBcryptHashHere...');
```

### In your application:
1. Try logging in with username `testuser` and password `test123`
2. Verify login succeeds
3. Check logs for any BCrypt-related errors

---

## Step 5: Security Best Practices

### After migration:
1. **Never log plaintext passwords** — remove any `System.out.println(password)` or similar
2. **Use HTTPS** — plaintext passwords transmitted over HTTP can be intercepted
3. **Add password complexity rules** — enforce minimum length, special characters, etc.
4. **Implement account lockout** — prevent brute-force by locking accounts after N failed attempts
5. **Add password reset flow** — email-based or admin-assisted reset
6. **Audit logs** — you already have `credential_changes_log`; ensure it logs failed login attempts too

### Example password policy:
- Minimum 8 characters
- At least 1 uppercase, 1 lowercase, 1 digit, 1 special character
- Not same as username
- Not in common password list (e.g., "password123")

---

## Rollback Plan (if needed)

If you need to rollback to SHA-256 (not recommended):
```sql
-- Restore from backup
DROP TABLE user;
CREATE TABLE user AS SELECT * FROM user_backup_before_bcrypt;
```

Then revert code changes (checkout previous commit).

---

## Verification Checklist

- [ ] jBCrypt jar downloaded and added to project classpath
- [ ] Project compiles without errors (`PasswordUtil` resolves)
- [ ] Database backup created (optional but recommended)
- [ ] All existing passwords migrated (reset or rehashed)
- [ ] Test login succeeds with new BCrypt passwords
- [ ] Password change functionality works (updates to BCrypt)
- [ ] New user creation works (stores BCrypt hashes)
- [ ] No plaintext passwords in logs or error messages

---

## FAQ

**Q: Can old SHA-256 passwords still work during migration?**  
A: No. BCrypt.checkpw() will fail on SHA-256 hashes. You must migrate all passwords first (Option A or B above).

**Q: What's the BCrypt cost factor (rounds)?**  
A: Currently set to 12 in `PasswordUtil.BCRYPT_ROUNDS`. This is a good balance (takes ~300ms to hash on modern hardware). You can increase to 13-14 for higher security at the cost of slower login/password changes.

**Q: How do I generate a BCrypt hash for testing?**  
A: Use the Java snippet:
```java
import util.PasswordUtil;
public class GenerateHash {
    public static void main(String[] args) {
        System.out.println(PasswordUtil.hashPassword("your_test_password"));
    }
}
```

**Q: Is BCrypt compatible with Java 21?**  
A: Yes. jBCrypt works with Java 8+ including Java 21.

---

## Support

If you encounter issues:
1. Check that `lib/jbcrypt-0.4.jar` is in classpath
2. Verify import `org.mindrot.jbcrypt.BCrypt` resolves
3. Check database for BCrypt format hashes (start with `$2a$`, `$2b$`, or `$2y$`)
4. Review application logs for `Invalid hash format` errors (indicates SHA-256 hashes still present)
