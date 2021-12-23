set term pdf enhanced dashed font "Helvetica, 12"

#set xtics auto
#unset mxtics
#set ytics auto
#unset mytics
#unset title
#set grid
set key outside

set datafile missing NaN


################## 1 ##################
# Pour comparer le temps mis par un algo pour les trois choix différents de premiers robot (random, meilleur et pire choix).

#stats "0-3-0.txt" u 5
#set xrange [:]
#set yrange [:]
#set output "algo3-RandomMap-RandomChoice.pdf"
#set title "Algo3 on RandomMap with RandomChoice"
#set xlabel "nbRobots"
#set ylabel "time"
#plot "0-3-0.txt" using 4:($6==0?$5:1/0) title "MoyTime" with linespoints pointtype 7 pointsize 0.5, "0-3-0.txt" using 4:($6==1?$5:1/0) title "BestTime" with linespoints pointtype 7 pointsize 0.5, "0-3-0.txt" using 4:($6==2?$5:1/0) title "WorstTime" with linespoints pointtype 7 pointsize 0.5


################## 2 ##################

# Pour comparer les algorithmes selon leurs temps (moyenne | meilleur | pire) sur la config Random :


#stats "0-1-0.txt" u 5
#set xrange [:]
#set yrange [:STATS_max]
#set xlabel "nbRobots"
#set ylabel "time"

#Moyenne resType = 0 :
#set output "MoyTime_AllAlgorithms_RandomMap-RandomChoice.pdf"
#set title "MoyTime on RandomMap with RandomChoice"
#plot "0-1-0.txt" using 4:($6==0?$5:1/0) title "algo1" with linespoints pointtype 7 pointsize 0.5, "0-2-0.txt" using 4:($6==0?$5:1/0) title "algo2" with linespoints pointtype 7 pointsize 0.5, "0-3-0.txt" using 4:($6==0?$5:1/0) title "algo3" with linespoints pointtype 7 pointsize 0.5, "0-4-0.txt" using 4:($6==0?$5:1/0) title "algo4" with linespoints pointtype 7 pointsize 0.5, "0-5-0.txt" using 4:($6==0?$5:1/0) title "algo5" with linespoints pointtype 7 pointsize 0.5, "0-6-0.txt" using 4:($6==0?$5:1/0) title "algo6" with linespoints pointtype 7 pointsize 0.5, "0-7-0.txt" using 4:($6==0?$5:1/0) title "algo7" with linespoints pointtype 7 pointsize 0.5, "0-8-0.txt" using 4:($6==0?$5:1/0) title "algo8" with linespoints pointtype 7 pointsize 0.5

#Meilleur resType = 1 :
#set output "BestTime_AllAlgorithms_RandomMap-RandomChoice.pdf"
#set title "BestTime on RandomMap with RandomChoice"
#plot "0-1-0.txt" using 4:($6==1?$5:1/0) title "algo1" with linespoints pointtype 7 pointsize 0.5, "0-2-0.txt" using 4:($6==1?$5:1/0) title "algo2" with linespoints pointtype 7 pointsize 0.5, "0-3-0.txt" using 4:($6==1?$5:1/0) title "algo3" with linespoints pointtype 7 pointsize 0.5, "0-4-0.txt" using 4:($6==1?$5:1/0) title "algo4" with linespoints pointtype 7 pointsize 0.5, "0-5-0.txt" using 4:($6==1?$5:1/0) title "algo5" with linespoints pointtype 7 pointsize 0.5, "0-6-0.txt" using 4:($6==1?$5:1/0) title "algo6" with linespoints pointtype 7 pointsize 0.5

#Pire resType = 2 :
#set output "WorstTime_AllAlgorithms_RandomMap-RandomChoice.pdf"
#set title "WorstTime on RandomMap with RandomChoice"
#plot "0-1-0.txt" using 4:($6==2?$5:1/0) title "algo1" with linespoints pointtype 7 pointsize 0.5, "0-2-0.txt" using 4:($6==2?$5:1/0) title "algo2" with linespoints pointtype 7 pointsize 0.5, "0-3-0.txt" using 4:($6==2?$5:1/0) title "algo3" with linespoints pointtype 7 pointsize 0.5, "0-4-0.txt" using 4:($6==2?$5:1/0) title "algo4" with linespoints pointtype 7 pointsize 0.5, "0-5-0.txt" using 4:($6==2?$5:1/0) title "algo5" with linespoints pointtype 7 pointsize 0.5, "0-6-0.txt" using 4:($6==2?$5:1/0) title "algo6" with linespoints pointtype 7 pointsize 0.5



################## 3 ##################

# Pour comparer les algorithmes selon leurs temps (moyenne | meilleur | pire) sur la config6 :


#set key outside
#set ylabel "time"
#set border 1
#set ytics nomirror
#unset xtics
#set grid


#Moyenne resType = 0 :
#set output "MoyTime_AllAlgorithms_Config6-RandomChoice.pdf"
#set title "MoyTime on Config6 with RandomChoice"
#plot "6-1-0.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo1", "6-2-0.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo2", "6-3-0.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo3", "6-4-0.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo4", "6-5-0.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+3", "6-5-0_algo4.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+4", "6-6-0.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo6", "6-7-0.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo7", "6-8-0.txt" using (0):($6==0?$5:1/0) with points pointtype 6 pointsize 1 title "algo8"


#Meilleur resType = 1 :
#set output "BestTime_AllAlgorithms_Config6-RandomChoice.pdf"
#set title "BestTime on Config6 with RandomChoice"
#plot "6-1-0.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo1", "6-2-0.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo2", "6-3-0.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo3", "6-4-0.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo4", "6-5-0.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+3", "6-5-0_algo4.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+4", "6-6-0.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo6"

#Pire resType = 2 :
#set output "WorstTime_AllAlgorithms_Config6-RandomChoice.pdf"
#set title "WorstTime on Config6 with RandomChoice"
#plot "6-1-0.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo1", "6-2-0.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo2", "6-3-0.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo3", "6-4-0.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo4", "6-5-0.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+3", "6-5-0_algo4.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+4", "6-6-0.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo6"


################## 4 ##################

# Pour comparer les temps de l'algo5 sur la config Random :

#stats "0-5-0_b.txt" u 5
#set xrange [:]
#set yrange [:]
#set xlabel "nbRobots"
#set ylabel "time"


#set output "Algo5_RandomMap-RandomChoice.pdf"
#set title "Algo5 on RandomMap with RandomChoice"
#plot "0-5-0_b.txt" using 4:($6==0?$5:1/0) title "moyenne" with linespoints pointtype 7 pointsize 0.5, "0-5-0_b.txt" using 4:($6==1?$5:1/0) title "meilleur" with linespoints pointtype 7 pointsize 0.5, "0-5-0_b.txt" using 4:($6==2?$5:1/0) title "pire" with linespoints pointtype 7 pointsize 0.5

################## 5 ##################

# Pour comparer les algorithmes algo5+6, algo5+4, algo5+3 selon leurs temps (moyenne | meilleur | pire) sur la config Random :


#set xrange [:]
#set yrange [:]
#set xlabel "nbRobots"
#set ylabel "time"

#Moyenne resType = 0 :
#set output "MoyTime_Algo5opti_RandomMap-RandomChoice.pdf"
#set title "MoyTime on RandomMap with RandomChoice"
#plot "0-5-0_b.txt" using 4:($6==0?$5:1/0) title "algo5+3" with linespoints pointtype 7 pointsize 0.5, "0-5-0_b_algo4.txt" using 4:($6==0?$5:1/0) title "algo5+4" with linespoints pointtype 7 pointsize 0.5, "0-5-0_b_algo6.txt" using 4:($6==0?$5:1/0) title "algo5+6" with linespoints pointtype 7 pointsize 0.5

#Meilleur resType = 1 :
#set output "BestTime_Algo5opti_RandomMap-RandomChoice.pdf"
#set title "BestTime on RandomMap with RandomChoice"
#plot "0-5-0_b.txt" using 4:($6==1?$5:1/0) title "algo5+3" with linespoints pointtype 7 pointsize 0.5, "0-5-0_b_algo4.txt" using 4:($6==1?$5:1/0) title "algo5+4" with linespoints pointtype 7 pointsize 0.5, "0-5-0_b_algo6.txt" using 4:($6==1?$5:1/0) title "algo5+6" with linespoints pointtype 7 pointsize 0.5

#Pire resType = 2 :
#set output "WorstTime_Algo5opti_RandomMap-RandomChoice.pdf"
#set title "WorstTime on RandomMap with RandomChoice"
#plot "0-5-0_b.txt" using 4:($6==2?$5:1/0) title "algo5+3" with linespoints pointtype 7 pointsize 0.5, "0-5-0_b_algo4.txt" using 4:($6==2?$5:1/0) title "algo5+4" with linespoints pointtype 7 pointsize 0.5, "0-5-0_b_algo6.txt" using 4:($6==2?$5:1/0) title "algo5+6" with linespoints pointtype 7 pointsize 0.5

################## 6 ##################

# Pour comparer algo5+6, 5+4 et 5+3 selon leurs temps (moyenne | meilleur | pire) sur la config9 :

#set key outside
#set ylabel "time"
#set border 1
#set ytics nomirror
#unset xtics
#set grid


#Moyenne resType = 0 :
#set output "MoyTimes_Algos5_Config9-RandomChoice.pdf"
#set title "MoyTime on Config9 with RandomChoice"
#plot "9-5-0_algo5+4.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+4 MoyTime", "9-5-0_algo5+3.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+3 MoyTime", "9-5-0_algo5+6.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+6 MoyTime"

#Meilleur resType = 1 :
#set output "BestTimes_Algos5_Config9-RandomChoice.pdf"
#set title "BestTime on Config9 with RandomChoice"
#plot "9-5-0_algo5+4.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+4 MoyTime", "9-5-0_algo5+3.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+3 MoyTime", "9-5-0_algo5+6.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+6 MoyTime"

#Pire resType = 2 :
#set output "WorstTimes_Algos5_Config9-RandomChoice.pdf"
#set title "WorstTime on Config9 with RandomChoice"
#plot "9-5-0_algo5+4.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+4 MoyTime", "9-5-0_algo5+3.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+3 MoyTime", "9-5-0_algo5+6.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+6 MoyTime"

#Tout sur un seul graphique :
#plot "9-5-0_algo5+4.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+4 MoyTime", "9-5-0_algo5+3.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+3 MoyTime", "9-5-0_algo5+4.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+4 BestTime", "9-5-0_algo5+3.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+3 BestTime", "9-5-0_algo5+4.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+4 WorstTime", "9-5-0_algo5+3.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+3 WorstTime", "9-5-0_algo5+6.txt" using (0):($6==2?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+6 WorstTime", "9-5-0_algo5+6.txt" using (0):($6==0?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+6 MoyTime", "9-5-0_algo5+6.txt" using (0):($6==1?$5:1/0) with points pointtype 7 pointsize 1 title "algo5+6 BestTime"
