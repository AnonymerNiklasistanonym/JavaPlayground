#!/usr/bin/env python3

import os
import re
import subprocess
from pygments import highlight
from pygments.lexers import JavaLexer, TextLexer
from pygments.formatters import HtmlFormatter

output_html_path = "java_summary.html"
base_dir = "."

run_tests = False
run_main_files = True

mvn_test_command = ["mvn", "test"]
java_run_command = ["java"]

formatter = HtmlFormatter(linenos=True, style='monokai')
text_formatter = HtmlFormatter()

html_parts = [f"<html><head><style>{formatter.get_style_defs('.highlight')} td.linenos .normal {"{color: white;}"}</style></head><body>"]
java_files = []

for dirpath, _, filenames in os.walk(base_dir):
    for filename in filenames:
        if not filename.endswith(".java") and not filename.endswith("pom.xml"):
            continue
        full_path = os.path.join(dirpath, filename)
        # normalize to lowercase for the "test/java" check
        norm = os.path.normpath(full_path).lower()
        if os.path.sep + "test" + os.path.sep + "java" + os.path.sep in norm:
            continue
        java_files.append(full_path)

for file_path in sorted(java_files):
    if not run_tests and file_path.endswith("pom.xml"):
        continue

    rel_path = os.path.relpath(file_path, base_dir)
    html_parts.append(f"<h2>{rel_path}</h2>")

    if file_path.endswith("pom.xml"):
        pom_dir = os.path.dirname(file_path)
        print(f"Run {" ".join(mvn_test_command)!r} in {pom_dir!r}")
        try:
            # Run mvn test, capture stdout+stderr
            result = subprocess.run(
                mvn_test_command,
                cwd=pom_dir,
                stdout=subprocess.PIPE,
                stderr=subprocess.STDOUT,
                encoding='utf-8',
                check=False,  # we capture even if tests fail
            )
            test_output = result.stdout or "(no output)"
        except Exception as e:
            test_output = f"Error running {" ".join(mvn_test_command)!r}: {e!r}"
        print(f"Output:\n{test_output}")
        # Syntax‑highlight as plain text
        html_parts.append(
            highlight(test_output, TextLexer(), text_formatter)
        )
    else:
        with open(file_path, 'r', encoding='utf-8') as f:
            code = f.read()
        html_parts.append(highlight(code, JavaLexer(), formatter))

        if run_main_files:
            file_dir = os.path.dirname(file_path)
            file_name = os.path.basename(file_path)
            print(f"Run {" ".join(java_run_command)!r} {file_name!r} in {file_dir!r}")
            try:
                # Run mvn test, capture stdout+stderr
                result = subprocess.run(
                    [*java_run_command, file_name],
                    cwd=file_dir,
                    stdout=subprocess.PIPE,
                    stderr=subprocess.STDOUT,
                    encoding='utf-8',
                    timeout=5,    # stop capturing after 5s
                    check=False,  # we capture even if command fails
                )
                test_output = result.stdout
                if test_output and not "error: " in test_output:
                    # Syntax‑highlight as plain text
                    html_parts.append(
                        highlight(test_output, TextLexer(), text_formatter)
                    )
            except Exception as e:
                test_output = f"Error running {" ".join(java_run_command)!r}: {e!r}"
            print(f"Output:\n{test_output}")

html_parts.append("</body></html>")

with open(output_html_path, "w", encoding="utf-8") as f:
    f.write('\n'.join(html_parts))

print(f"HTML summary generated: {output_html_path}")
