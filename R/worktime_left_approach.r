# libraries
attach(mtcars) #for multiple plots
library(data.table) #for cartesian product

#functions-------------------------------------------------------------

# function to calculate number of discussed topics, depending on probability to discuss topic
calcDiscussedTopics <- function(number_of_communications, probability){
  discussedTopics <- 0
  decrease <- probability / 30
  p <- probability
  for(runs in 0:number_of_communications)
  {
    random <- runif(1, 0.0, 1.0)
    
    if(random<=p){
      discussedTopics = discussedTopics+1
    }
    p <- p - decrease
  }
  return(discussedTopics)
}

# function to calculate number of discussed topics, depending on probability to discuss topic
calcDiscussedTopics2 <- function(number_of_communications, probability, max_topics){
  discussedTopics <- 0
  decrease <- probability / max_topics
  p <- probability
  for(runs in 0:number_of_communications)
  {
    random <- runif(1, 0.0, 1.0)
    
    if(random<=p){
      discussedTopics = discussedTopics+1
      p <- p - decrease
    }
  }
  return(discussedTopics)
}

# function for plot printing
plotLineWithRange <- function(x, yVal, yMin, yMax,
                              lineColor="Black", rangeColor="LightBlue",
                              main="", xlab="X", ylab="Y"){
  if(missing(x)){
    x <- 1:length(yVal)
  }
  stopifnot(length(yVal) == length(yMin) && length(yVal) == length(yMax))
  
  plot(x=c(min(x),max(x)),y=c(min(yMin),max(yMax)),type="n", main=main,xlab=xlab,ylab=ylab)
  polygon(x=c(x,rev(x)),y=c(yMax,rev(yVal)),col=rangeColor,border=NA)
  polygon(x=c(x,rev(x)),y=c(yMin,rev(yVal)),col=rangeColor,border=NA)
  lines(x=x,y=yVal,col=lineColor)
}

#initial data------------------------------------------------------------

#amount of topics that are enough for task solving
comNumber <- 30
medias_number <- 3

#names of the media for plot
media_name <- array(c("Face-to-face","Phone", "E-mail"), c(medias_number,1))
#probability to recieve answer during the one communication
probability <- c(0.9, 0.6, 0.3)

# STEP 2 ============================================================================

#possible communication frequencies per media
communication_frequencies<-array(0, c(medias_number,comNumber))
for(m in 1:medias_number){
  communication_frequencies[m,]<-c(0:(comNumber-1))
}

rows <- nrow(communication_frequencies)
cols <- ncol(communication_frequencies)

discussedTopicsNumber <- array(0, c(medias_number,comNumber))
discussedTopicsNumber2 <- discussedTopicsNumber
discussedTopicsNumber3 <- discussedTopicsNumber
# STEP 1 - Calculate dependence between communication frequency and the number of discussed topic
times <- 100
tempDiscussedTopicsNumber <- array(0, c(comNumber, times, medias_number))
tempDiscussedTopicsNumber2 <- tempDiscussedTopicsNumber
tempDiscussedTopicsNumber3 <- tempDiscussedTopicsNumber
for(t in 1:times){
  for(m in 1:medias_number){
    #discussedTopics <- 0
    #temp <- 0
    for(c in 1:comNumber){
      tempDiscussedTopicsNumber[c, t, m] <- calcDiscussedTopics(communication_frequencies[m,c], probability[m])
      tempDiscussedTopicsNumber2[c, t, m] <- calcDiscussedTopics2(communication_frequencies[m,c], probability[m], 5)
      tempDiscussedTopicsNumber3[c, t, m] <- calcDiscussedTopics2(communication_frequencies[m,c], probability[m], 5)
    }
  }
}

for(m in 1:medias_number){
  discussedTopicsNumber[m,] <- rowMeans(tempDiscussedTopicsNumber[,,m])
  discussedTopicsNumber2[m,] <- rowMeans(tempDiscussedTopicsNumber2[,,m])
  discussedTopicsNumber3[m,] <- rowMeans(tempDiscussedTopicsNumber3[,,m])
}

# worktimeleft approach ===========================================

par(mfrow=c(1,3))

for(m in 1:3)
{
  x <-array(0,c(1,30))
  x[1] <- 200
  plot(x[,], col="white", pch=".")
  
  for(i in 0:29){
    for(t in 0:300){
      
      r <- sample(0:29, 3)
      
      #runif(3, 0, 29)
      s <- sum(r)
      
      if(s > 29)
      {
        f <- 29/s
        r <- r * f
        s <- s*f
      }
      
      
      x <-array(0,c(1,1))
      y <-array(0,c(1,1))
      
      x[1] <- (29-s) * (r[1]*probability[1] + r[2]*probability[2] +r[3]*probability[3])
      y[1] <- s
      
      
      ccol = "green"
      z <- r[m]
      if(z>0 && z<10){
        ccol = "green"
      } else if(z<20){
        ccol = "blue"
      } else {
        ccol = "red"
      }
      
      points(y,x, col=ccol, pch=8)
    }
  }
  title(media_name[m])
}


#plot communication frequencies -------------------------------------------------------------------------

par(mfrow=c(1,2),oma=c(0,0,2,0),mar = c(5,4,2,1))


#synchron change of the communications - e.g. email/ftf = 1/1 ... 29/29

dat <- t(discussedTopicsNumber) # make data
matplot(dat, type = c("b"), pch=1, col = 1:medias_number,
        xlab = "Communication frequencies",   # Add a label on the x-axis
        ylab = "Contribution of the media into the communication",
        main="Contribution per media")
legend("topleft", legend = media_name, col=1:3, pch=1) # optional legend
title("Synchronous communication frequencies", outer=TRUE)

plot(c(0:29), colSums(discussedTopicsNumber), xlab = "Communication frequencies",   # Add a label on the x-axis
      ylab = "# of discussed topics", main ="Number of discussed topics sum")
     

#nonsynchron change of the communications 
par(mfrow=c(1,2),oma=c(0,0,2,0),mar = c(5,4,2,1))

dat <- t(discussedTopicsNumber3) # make data
matplot(dat, type = c("b"), pch=1, col = 1:medias_number,
        xlab = "Communication frequencies",   # Add a label on the x-axis
        ylab = "# of discussed topics") #plot
legend("topleft", legend = media_name, col=1:3, pch=1) # optional legend

dat <- t(discussedTopicsNumber2) # make data
matplot(dat, type = c("b"), pch=1, col = 1:medias_number,
        xlab = "Communication frequencies",   # Add a label on the x-axis
        ylab = "# of discussed topics") #plot
legend("topleft", legend = media_name, col=1:3, pch=1) # optional legend


discussedTopicsNumber <- discussedTopicsNumber2

#STEP 3 ====================================================================

cp <- CJ(discussedTopicsNumber[1,],discussedTopicsNumber[2,],discussedTopicsNumber[3,])
cp <- as.matrix(cp)



#calculate indexes
indexes <- array(0, c(medias_number,comNumber))
for(m in 1:medias_number){
  indexes[m,]<-c(1:comNumber)
}

indexes <- CJ(indexes[1,],indexes[2,],indexes[3,])
indexes <- as.matrix(indexes)

rss <- rowSums(indexes)
rows <- nrow(rss)

temp1 <-c()
for(i in 1:length(rss)){
  if(rss[i]>30){
    temp1 <- c(temp1, i)
  }
}
indexes <- indexes[-temp1,]
#sum rows
rs <- rowSums(cp[-temp1,])



# penalties - penalties calculated per communication actions
temp <- cp #com-n frequencies are equal to the indexes minus 1
pen_rs <- rowSums(temp)

# calculate disjoint negative influence for each media
t <- temp
for(i in 1:nrow(temp)){
  for(m in 1:medias_number){
    t[i,m] <- t[i,m]*probability[m]
  }
}
d_pen_rs <- rowSums(t) #calculate disjoint penalties

# penalties - negative influence
uniform_penalties <- array(0, c(comNumber, comNumber^(medias_number-1),medias_number))
disjoint_penalties <- uniform_penalties #initialize size

# iterate over indexes
values <- array(0, c(comNumber, comNumber^(medias_number-1),medias_number))

#help array, that shows how many communications combinations were done for each frequency
numbers <- array(0, c(medias_number, comNumber^(medias_number-1)))

rows <- nrow(indexes)
for(r in 1:rows){
  for(m in 1:medias_number){
    #cat("; -1- ")
    #cat(paste(" index",index))
    #cat(paste(" r",r))
    #cat(paste(" m",m))
    
    index <- indexes[r,m]
    numbers[m, index] <- numbers[m, index] + 1 
    #cat("; -2- ")
    #values[index, numbers[m, index],  m] <- rs[r]
    #if(rs[r]>30){
      #values[index, numbers[m, index],  m] <- 30
    #}
    values[index, numbers[m, index],  m] <- (30 - sum(indexes[r,])) * (d_pen_rs[r])
    
    #uniform_penalties[index, numbers[m, index], m] <- pen_rs[r]
    #disjoint_penalties[index, numbers[m, index], m] <- d_pen_rs[r]
  }
}

#PLOTS==============================================================================
while(!par('page')) plot.new()
par(mfrow=c(1,medias_number),oma=c(0,0,2,0))

for(m in 1:medias_number){
  TEST <- values[,,m]
  # compute mean, min and max of rows
  means <- rowMeans(TEST)
  maxs <- apply(TEST,1,max)
  mins <- apply(TEST,1,min)
  
  # create x-coordinates
  xcoords <- 1:nrow(TEST)
  plotLineWithRange(yVal=means,yMin=mins,yMax=maxs,
                    main=media_name[m,],
                    xlab = "Communication frequencies",   # Add a label on the x-axis
                    ylab = "# of discussed topics")        # Add a label on the y-axis))
}

#
# plot disjoint_penalties-------------------------------------------
while(!par('page')) plot.new()
par(mfrow=c(3,medias_number),mar = rep(2, 4),oma=c(0,0,2,0))

for(i in c(1,2,3)){
  dp <- disjoint_penalties/i
  vc <- values - dp
  for(m in 1:medias_number){
    TEST <- vc[,,m]
    # compute mean, min and max of rows
    means <- rowMeans(TEST)
    maxs <- apply(TEST,1,max)
    mins <- apply(TEST,1,min)
    
    # create x-coordinates
    xcoords <- 1:nrow(TEST)
    plotLineWithRange(yVal=means,yMin=mins,yMax=maxs,
                      main = media_name[m,],
                      xlab = "Communication frequencies",   # Add a label on the x-axis
                      ylab = "Influence")
  }
}
title("Joint communication minus NONLINEAR DISJOINT penalties", outer=TRUE)


