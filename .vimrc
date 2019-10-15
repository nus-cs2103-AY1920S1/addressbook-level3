set tabstop=4
set softtabstop=4
set shiftwidth=4
set colorcolumn=121
autocmd BufWritePre * %s/\s\+$//e
