_() { sed -n 's/./|&/2g; G; h; $ { x; s/\n//g; y/|/\n/; p }'; }
reverse() { <<< "$*" _|_; }
