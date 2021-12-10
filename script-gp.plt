set term pdf size 3.5,2.5 enhanced monochrome dashed font "Helvetica, 12"

#set xtics auto
#unset mxtics
#set ytics auto
#unset mytics
set xrange [:]
set yrange [:]
#unset title
#set key top left
set key top right
#set grid

set output "algo3.pdf"
set xlabel "nbRobots"
set ylabel "time"
plot "0-3-0_13:41:37.txt" using 4:5 title "firstRandom" with lines, "0-3-1_13:43:56.txt" using 4:5 title "firstBetter" with lines, "0-3-2_13:47:13.txt" using 4:5 title "firstWorst" with lines
#plot "0-3-0_13:08:27.txt" using 4:5 title "firstRandom"