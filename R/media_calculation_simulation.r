# libraries
attach(mtcars) #for multiple plots
library(data.table) #for cartesian product

# functions ===========================================

# function to calculate number of discussed topics, depending on probability to discuss topic
calcDiscussedTopics <- function(number_of_communications, probability){
  discussedTopics <- 0
  decrease <- probability / 25
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
calcDiscussedTopics2 <- function(number_of_communications, probability){
  discussedTopics <- 0
  decrease <- probability / 5
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

# initial data =========================================

#amount of topics that are enough for task solving
comNumber <- 30
medias_number <- 3

#names of the media for plot
media_name <- array(c("Face-to-face","Phone", "E-mail"), c(medias_number,1))
#probability to recieve answer during the one communication
probability <- c(0.9, 0.6, 0.3)


#possible communication frequencies per media
communication_frequencies<-array(0, c(medias_number,comNumber))
for(m in 1:medias_number){
  communication_frequencies[m,]<-c(0:(comNumber-1))
}

rows <- nrow(communication_frequencies)
cols <- ncol(communication_frequencies)

discussedTopicsNumber <- array(0, c(medias_number,comNumber))
discussedTopicsNumber2 <- discussedTopicsNumber

# STEP 1 - Calculate dependence between communication frequency and the number of discussed topic
times <- 100
tempDiscussedTopicsNumber <- array(0, c(comNumber, times, medias_number))
tempDiscussedTopicsNumber2 <- tempDiscussedTopicsNumber 

for(t in 1:times){
  for(m in 1:medias_number){
    for(c in 1:comNumber){
      tempDiscussedTopicsNumber[c, t, m] <- calcDiscussedTopics(communication_frequencies[m,c], probability[m])
      tempDiscussedTopicsNumber2[c, t, m] <- calcDiscussedTopics2(communication_frequencies[m,c], probability[m])
    }
  }
}

for(m in 1:medias_number){
  discussedTopicsNumber[m,] <- rowMeans(tempDiscussedTopicsNumber[,,m])
  discussedTopicsNumber2[m,] <- rowMeans(tempDiscussedTopicsNumber2[,,m])
}

# plot communication frequencies =========================

par(mfrow=c(1,2),oma=c(0,0,2,0),mar = c(5,4,2,1))

dat <- t(discussedTopicsNumber) # make data
matplot(dat, type = c("b"), pch=1, col = 1:medias_number,
        xlab = "Communication frequencies",   # Add a label on the x-axis
        ylab = "Contribution of the media into the communication")
legend("topleft", legend = media_name, col=1:3, pch=1) # optional legend
title("Dependence between communication frequency and the number of discussed topic", outer=TRUE)

dat <- t(discussedTopicsNumber2) # make data
matplot(dat, type = c("b"), pch=1, col = 1:medias_number,
        xlab = "Communication frequencies",   # Add a label on the x-axis
        ylab = "# of discussed topics") #plot
legend("topleft", legend = media_name, col=1:3, pch=1) # optional legend

#synchron change of the communications - e.g. email/ftf = 1/1 ... 29/29
par(mfrow=c(1,1))
plot(c(0:29), colSums(discussedTopicsNumber), xlab = "Communication frequencies",   # Add a label on the x-axis
     ylab = "# of discussed topics", main ="Number of discussed topics sum - Synchronous communication change")

# STEP 2 ===============================================
par(mfrow=c(1,3))
#make cartesian product
cp <- CJ(discussedTopicsNumber[1,],discussedTopicsNumber[2,],discussedTopicsNumber[3,])
cp <- as.matrix(cp)

#sum rows
rs <- rowSums(cp)

#calculate indexes
indexes <- array(0, c(medias_number,comNumber))
for(m in 1:medias_number){
  indexes[m,]<-c(1:comNumber)
}

indexes <- CJ(indexes[1,],indexes[2,],indexes[3,])
indexes <- as.matrix(indexes)

rows <- nrow(indexes)

# penalties - penalties calculated per communication actions
temp <- indexes - 1 #com-n frequencies are equal to the indexes minus 1
pen_rs <- rowSums(temp)
t <- temp
for(m in 1:medias_number){
  t[,m] <- temp[,m] * probability[m]
}
d_pen_rs <- rowSums(t)

# penalties - negative influence
uniform_penalties <- array(0, c(comNumber, comNumber^(medias_number-1),medias_number))
disjoint_penalties <- uniform_penalties

# iterate over indexes
values <- array(0, c(comNumber, comNumber^(medias_number-1),medias_number))

#help array, that shows how many communications combinations were done for each frequency
numbers <- array(0, c(medias_number, comNumber^(medias_number-1)))

for(r in 1:rows){
  for(m in 1:medias_number){
    index <- indexes[r,m]
    numbers[m, index] <- numbers[m, index] + 1 
    values[index, numbers[m, index],  m] <- rs[r]
    uniform_penalties[index, numbers[m, index], m] <- pen_rs[r]
    disjoint_penalties[index, numbers[m, index], m] <- d_pen_rs[r]
  }
}

# PLOTS ================================================
plot.new() #force new page for plot
frame()
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
                    ylab = "# of discussed topics")        # Add a label on the y-axis
}
title("Joint communication", outer=TRUE)

# plot uniform_penalties ===================================
par(mfrow=c(3,medias_number),mar = rep(2, 4),oma=c(0,0,2,0))

for(i in c(2.6,4,7)){
  v <- values - uniform_penalties/i
  for(m in 1:medias_number){
    TEST <- v[,,m]
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
title("Joint communication minus UNIFORM penalties", outer=TRUE)

# plot disjoint_penalties ============================
for(i in c(1,2.4,7)){
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
title("Joint communication minus DISJOINT penalties", outer=TRUE)

# worktime left dependence ===============================
par(mfrow=c(1,3))

for(m in 1:medias_number)
{
  x <-array(0,c(1,30))
  x[1] <- 200
  plot(x[,], col="white", pch=".")
  
  for(i in 0:29){ # for available comm frequencies
    for(t in 0:300){ # 300 times/points
      r <- sample(0:29, medias_number) # generate comm frequencies
      
      #limit frequencies up to 30
      commFrequency <- sum(r)
      if(commFrequency > 29)
      {
        f <- 29/s
        r <- r * f
        commFrequency <- commFrequency * f
      }
      # init points
      x <-array(0,c(1,1))
      y <-array(0,c(1,1))
      
      #calculate dependence
      positiveInfluence <- c(0)
      for(mm in 1:medias_number)
      {
        positiveInfluence <- positiveInfluence + r[mm]*probability[mm]
      }
      
      #time left * positiveInfluence
      x[1] <- (29-commFrequency) * positiveInfluence
      y[1] <- commFrequency
      
      ccol = "black"
      z <- r[m]
      if(z<10){
        ccol = "green"
      } else if(z<20){
        ccol = "blue"
      } else {
        ccol = "red"
      }
      
      points(y,x, col=ccol, pch=8) #draw point
    }
  }
  title(media_name[m])
}
