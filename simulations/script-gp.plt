set term pdf size 3.5,2.5 enhanced dashed font "Helvetica, 12"

#set xtics auto
#unset mxtics
#set ytics auto
#unset mytics
#unset title
#set grid

set datafile missing NaN


################## 1 ##################
# Pour comparer le temps mis par un algo pour les trois choix différents de premiers robot (random, meilleur et pire choix).

#set key bottom right

#stats "0-3-0.txt" u 5
#set xrange [:]
#set yrange [:STATS_max]
#set output "algo3-RandomMap-RandomChoice.pdf"
#set title "Algo3 on RandomMap with RandomChoice"
#set xlabel "nbRobots"
#set ylabel "time"
#plot "0-3-0.txt" using 4:($6==0?$5:1/0) title "MoyTime" with linespoints, "0-3-0.txt" using 4:($6==1?$5:1/0) title "BestTime" with linespoints, "0-3-0.txt" using 4:($6==2?$5:1/0) title "WorstTime" with linespoints

################## 2 ##################
# Pour comparer des algorithmes selon leur meilleur temps

set key top left

stats "0-1-0.txt" u 5
set xrange [:]
set yrange [:STATS_max]
set output "MoyTime_AllAlgorithms_RandomMap-RandomChoice.pdf"
set title "MoyTime on RandomMap with RandomChoice"
set xlabel "nbRobots"
set ylabel "time"
#plot "0-1-0.txt" using 4:($6==1?$5:1/0) title "algo1" with linespoints, "0-2-0.txt" using 4:($6==1?$5:1/0) title "algo2" with linespoints, "0-3-0.txt" using 4:($6==1?$5:1/0) title "algo3" with linespoints, "0-4-0.txt" using 4:($6==1?$5:1/0) title "algo4" with linespoints, "0-5-0.txt" using 4:($6==1?$5:1/0) title "algo5" with linespoints
#plot "0-1-0.txt" using 4:($6==0?$5:1/0) title "algo1" with linespoints, "0-2-0.txt" using 4:($6==0?$5:1/0) title "algo2" with linespoints, "0-3-0.txt" using 4:($6==0?$5:1/0) title "algo3" with linespoints, "0-4-0.txt" using 4:($6==0?$5:1/0) title "algo4" with linespoints, "0-5-0.txt" using 4:($6==0?$5:1/0) title "algo5" with linespoints
plot "0-1-0.txt" using 4:($6==2?$5:1/0) title "algo1" with linespoints, "0-2-0.txt" using 4:($6==2?$5:1/0) title "algo2" with linespoints, "0-3-0.txt" using 4:($6==2?$5:1/0) title "algo3" with linespoints, "0-4-0.txt" using 4:($6==2?$5:1/0) title "algo4" with linespoints, "0-5-0.txt" using 4:($6==2?$5:1/0) title "algo5" with linespoints



