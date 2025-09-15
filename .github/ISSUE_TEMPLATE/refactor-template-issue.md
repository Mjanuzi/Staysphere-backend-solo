---
name: Refactor template issue
about: Use as template for refactor issues
title: "[REFACTOR]"
labels: backend, Refactor
assignees: ''

---

## Purpose
Keep refactoring tasks simple and focused:
1. **Clear Goal**: What are we fixing and why?
2. **Simple Steps**: Easy-to-follow tasks
3. **Done Criteria**: Know when we're finished

---

## **What needs to be fixed?**

**Problem:** 
- [What SOLID principle is being violated? e.g., "ListingService is doing too many things"]

**Goal:** 
- [What we want to achieve, e.g., "Split into separate services with single responsibilities"]

**SOLID Focus:** [SRP/OCP/DIP - pick one main principle]

---

## **How do we know it's done?**

- [ ] All existing features still work
- [ ] New service/class has only one main job
- [ ] Constructor injection is used
- [ ] Basic tests are added
- [ ] Code follows team naming conventions

---

##  **Files to work with**

**Extract from:** `[Current file path]`
**Create new:** `[New file path]`
**Update tests:** `[Test file path]`

**Dependencies needed:**
- [List what repositories/services this new class needs]

---

## **Steps to complete**

1. **Create the new class**
   - [ ] Create new file with constructor injection
   - [ ] Add the methods we're moving

2. **Move the logic**
   - [ ] Copy methods from old service to new class
   - [ ] Make sure all logic works the same

3. **Update the original service**
   - [ ] Add new service as dependency
   - [ ] Replace old methods with calls to new service
   - [ ] Remove the moved methods

4. **Test it works**
   - [ ] Run existing tests to check nothing broke
   - [ ] Add basic test for new service
   - [ ] Test manually in Postman/browser

5. **Clean up**
   - [ ] Remove unused imports
   - [ ] Add basic comments
   - [ ] Ask for code review

---

## **Notes**

**This task depends on:** [List other issues that need to be done first]
**This blocks:** [List what issues are waiting for this one]

**Questions/Help:**
- Ask team before changing method signatures
- Test in development before pushing
- Keep a backup of working code

---

## **Definition of Done**
- [ ] Code compiles and runs
- [ ] All existing functionality works
- [ ] Basic tests pass
- [ ] Code reviewed by teammate
- [ ] Committed to git
