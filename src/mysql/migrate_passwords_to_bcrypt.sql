-- ================================================================
-- Password Migration Script: SHA-256 → BCrypt
-- ================================================================
-- Purpose: Reset all user passwords to a temporary BCrypt hash
--          Users will need to change password on first login
-- 
-- WARNING: This will replace all existing passwords!
--          Backup the user table first if needed.
-- ================================================================

USE payrollsystem_db;

-- Step 1: Create backup of user table (optional but recommended)
DROP TABLE IF EXISTS user_backup_sha256;
CREATE TABLE user_backup_sha256 AS SELECT * FROM user;

SELECT 'Backup created: user_backup_sha256' AS Status;

-- Step 2: Update all passwords to a temporary BCrypt hash
-- Temporary password: "ChangeMe2025!"
-- BCrypt hash (cost=12): $2a$12$LQv3c1yqBw1L4TmYd8XF1OwFzQvTnR3hKPjXvN8PqKm4sLnYtOb.S
-- 
-- Users must change this password on first login.

UPDATE user 
SET password = '$2a$12$LQv3c1yqBw1L4TmYd8XF1OwFzQvTnR3hKPjXvN8PqKm4sLnYtOb.S';

SELECT CONCAT('Updated ', ROW_COUNT(), ' user passwords to temporary BCrypt hash') AS Status;

-- Step 3: Verify migration
SELECT 
    COUNT(*) AS total_users,
    SUM(CASE WHEN password LIKE '$2a$%' OR password LIKE '$2b$%' OR password LIKE '$2y$%' 
        THEN 1 ELSE 0 END) AS bcrypt_hashes,
    SUM(CASE WHEN LENGTH(password) = 64 AND password NOT LIKE '$%' 
        THEN 1 ELSE 0 END) AS possible_sha256_hashes
FROM user;

-- Step 4: Show sample of migrated users
SELECT user_id, username, 
       LEFT(password, 20) AS password_prefix,
       LENGTH(password) AS password_length
FROM user
LIMIT 10;

-- ================================================================
-- NEXT STEPS:
-- 1. All users now have password: "ChangeMe2025!"
-- 2. Implement forced password change on first login
-- 3. Test login with temporary password
-- 4. Update application to prompt users for new password
-- ================================================================

-- Optional: Create admin/test accounts with known passwords
-- (Generate hashes first using Java: PasswordUtil.hashPassword("your_password"))
-- 
-- Example:
-- INSERT INTO user (emp_id, username, password) 
-- VALUES (9999, 'admin', '$2a$12$your_generated_hash_here');
