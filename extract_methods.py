#!/usr/bin/env python3

import os
import re
import glob

def extract_method_names():
    dao_files = glob.glob("src/com/eventApp/DAO/*.java")
    
    print("=== ALL METHOD NAMES IN DAO PACKAGE ===\n")
    
    total_methods = 0
    
    for dao_file in sorted(dao_files):
        class_name = os.path.basename(dao_file).replace('.java', '')
        print(f"=== {class_name} ===")
        
        with open(dao_file, 'r') as f:
            content = f.read()
        
        # Regex to find public method declarations
        # This matches: public [static] [return_type] methodName(
        method_pattern = r'public\s+(?:static\s+)?(?:[\w<>\[\],\s]+\s+)?(\w+)\s*\('
        
        methods = re.findall(method_pattern, content)
        
        # Filter out constructors (methods with same name as class)
        methods = [m for m in methods if m != class_name]
        
        # Count all methods including duplicates (overloaded methods)
        unique_methods = sorted(set(methods))
        method_count = len(methods)
        
        for method in unique_methods:
            # Count how many times this method appears (for overloads)
            count = methods.count(method)
            if count > 1:
                print(f"  {method} (overloaded {count} times)")
            else:
                print(f"  {method}")
        
        total_methods += method_count
        
        print()
    
    print(f"=== SUMMARY ===")
    print(f"Total DAO files: {len(dao_files)}")
    print(f"Total unique method names: {total_methods}")

if __name__ == "__main__":
    extract_method_names()