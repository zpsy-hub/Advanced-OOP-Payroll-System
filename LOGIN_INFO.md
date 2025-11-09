# Login Information

## After BCrypt Migration

**ALL users now use the same temporary password:**

```
Password: ChangeMe2025!
```

⚠️ **Important:** 
- Password is **case-sensitive**
- The exclamation mark `!` at the end is **required**
- Copy/paste it exactly: `ChangeMe2025!`

## Sample Usernames

You can login with any of these usernames:

- `agudel`
- `alvrod`
- `atiros`
- `baumar`
- `casjoh`
- `delkol`
- `delsel`
- `deltom`
- `farmar`
- `garman`

## Testing Login

### Command-line test:
```powershell
java -cp "build/classes;lib/*" QuickLoginTest
```

### Interactive test:
```powershell
java -cp "build/classes;lib/*" LoginTroubleshoot
```

### List all usernames:
```powershell
java -cp "build/classes;lib/*" ListUsers
```

## Troubleshooting

If login fails:
1. ✅ Make sure you're using the password exactly: `ChangeMe2025!`
2. ✅ Check that username exists (use ListUsers)
3. ✅ Verify MySQL is running and accessible
4. ✅ Confirm config.properties has correct database settings

## Next Steps

After successful login, you should:
1. Implement a forced password change on first login
2. Each user creates their own secure password
3. Delete the backup table `user_backup_sha256` after confirming everything works
