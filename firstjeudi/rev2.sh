reverse() { <<< "$*" sed 's/./\n&/2g; q' | sed -n 'G; h; $ { x; s/\n//g; p }'; }
