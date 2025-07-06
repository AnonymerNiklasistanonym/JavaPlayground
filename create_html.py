import os
import re
from pygments import highlight
from pygments.lexers import JavaLexer
from pygments.formatters import HtmlFormatter

output_html_path = "java_summary.html"
base_dir = "."  # Change this to your root path

formatter = HtmlFormatter(linenos=True, style='monokai')
html_parts = [f"<html><head><style>{formatter.get_style_defs('.highlight')} td.linenos .normal {"{color: white;}"}</style></head><body>"]

for dirpath, _, filenames in os.walk(base_dir):
    for filename in filenames:
        if filename.endswith(".java"):
            file_path = os.path.join(dirpath, filename)
            if os.path.sep + "test" + os.path.sep + "java" + os.path.sep in os.path.normpath(file_path).lower():
                continue  # Skip test/java files
            with open(file_path, 'r', encoding='utf-8') as f:
                code = f.read()
            rel_path = os.path.relpath(file_path, base_dir)
            html_parts.append(f"<h2>{rel_path}</h2>")
            html_parts.append(highlight(code, JavaLexer(), formatter))

html_parts.append("</body></html>")

with open(output_html_path, "w", encoding="utf-8") as f:
    f.write('\n'.join(html_parts))

print(f"HTML summary generated: {output_html_path}")
