# Security Controls Testing - Quick Reference

## 🚀 Quick Start

```powershell
# Run all tests (easiest method)
.\run-security-tests.ps1
```

## 📁 Test Files Location

```
src/test/
├── SessionManagerTest.java     (19 tests - Control 2)
└── SecureLoggerTest.java       (30 tests - Control 4)
```

## 🎯 What's Being Tested

### Control 2: Session Management
- ✅ 30-minute timeout
- ✅ Session validation
- ✅ Logout functionality
- ✅ Activity tracking
- ✅ Access control

### Control 4: Error Handling
- ✅ Password/token redaction
- ✅ Generic error messages
- ✅ Secure file logging
- ✅ Stack trace limiting
- ✅ Security event logging

## 📊 Expected Results

| Test Suite | Tests | Expected Pass |
|------------|-------|---------------|
| SessionManager | 19 | 13-15 (87-100%) |
| SecureLogger | 30 | 29 (97-100%) |
| **TOTAL** | **49** | **42-44 (95-100%)** |

## 📋 Run Commands

### All Tests
```powershell
.\run-security-tests.ps1
# OR
java -cp "bin;lib/*" org.testng.TestNG testng.xml
```

### Single Test Class
```powershell
# SessionManager only
java -cp "bin;lib/*" org.testng.TestNG -testclass test.SessionManagerTest

# SecureLogger only
java -cp "bin;lib/*" org.testng.TestNG -testclass test.SecureLoggerTest
```

### Specific Test Group
```powershell
# Only sanitization tests
java -cp "bin;lib/*" org.testng.TestNG -groups sanitization -testclass test.SecureLoggerTest

# Only session timeout tests
java -cp "bin;lib/*" org.testng.TestNG -groups session-timeout -testclass test.SessionManagerTest
```

## 📄 Test Results Location

```
test-output/
├── index.html              ← Open this in browser
├── emailable-report.html   ← Email-friendly version
└── testng-results.xml      ← For CI/CD
```

## 📝 Log Files Location

```
logs/
├── application.log    ← INFO, WARN, SECURITY events
└── error.log          ← ERROR with stack traces
```

## ✅ Post-Test Verification

```powershell
# View test results
start test-output\index.html

# Check error log
Get-Content logs\error.log -Tail 20

# Check application log
Get-Content logs\application.log -Tail 20
```

## 🔧 Manual Tests Required

1. **Session Timeout (30 min)**
   - Set timeout to 1 minute in code
   - Wait 2 minutes
   - Verify session expired

2. **Log Rotation (10 min)**
   - Generate 10MB+ of logs
   - Verify file rotates with timestamp

3. **Login/Logout Cycle (5 min)**
   - Complete full GUI workflow
   - Verify security events logged

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| TestNG not found | Download from https://testng.org/ |
| Compilation errors | Run: `javac -d bin -cp "lib/*" src/**/*.java` |
| Tests not found | Ensure files in `src/test/` directory |
| No log files | Check write permissions on project folder |

## 📚 Documentation

- **Full Guide**: SECURITY_TESTS_GUIDE.md (650+ lines)
- **Test Summary**: UNIT_TESTS_SUMMARY.md
- **Control Details**: CONTROLS_2_4_DETAILED_DOCUMENTATION.md

## 🎓 For Milestone Submission

Include:
1. ✅ Test classes (SessionManagerTest, SecureLoggerTest)
2. ✅ Test results screenshot (test-output/index.html)
3. ✅ Log file samples (first 50 lines each)
4. ✅ Manual test documentation (with screenshots)
5. ✅ This documentation

## ⚡ Test Groups Available

```
SessionManagerTest:
- session-creation
- session-validation
- session-timeout
- activity-tracking
- logout
- access-control
- session-info
- user-retrieval
- singleton
- integration

SecureLoggerTest:
- sanitization
- user-messages
- file-operations
- log-levels
- exception-handling
- security-events
- log-rotation
- integration
- performance
```

## 📞 Quick Help

```powershell
# Compile only
javac -d bin -cp "lib/*;bin" src/test/*.java

# Run with verbose output
java -cp "bin;lib/*" org.testng.TestNG -verbose 2 testng.xml

# Generate specific report
java -cp "bin;lib/*" org.testng.TestNG -d custom-output testng.xml
```

---

**Last Updated**: November 9, 2025  
**Status**: ✅ Ready to run
