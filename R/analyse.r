attach(mtcars) #for multiple plots
library(data.table) #for cartesian product
library(car)

oldw <- getOption("warn") #turn of warnings
options(warn = -1)

# function for plot printing
plotLineWithRange <- function(x, yVal, yMin, yMax,
                              lineColor="Black", rangeColor="LightBlue",
                              main="", xlab="X", ylab="Y"){
  if(missing(x)){
    x <- 1:length(yVal)
  }
  stopifnot(length(yVal) == length(yMin) && length(yVal) == length(yMax))
  
  plot(x=c(min(x),max(x)),y=c(min(yMin), max(yMax)),
       ylim=rev(range(c(min(yMin), max(yMax)))),
       type="n", main=main,xlab=xlab,ylab=ylab)
  polygon(x=c(x,rev(x)),y=c(yMax,rev(yVal)),col=rangeColor,border=NA)
  polygon(x=c(x,rev(x)),y=c(yMin,rev(yVal)),col=rangeColor,border=NA)
  lines(x=x,y=yVal,col=lineColor)
}

getNames <- function(fullName, path){
  name <- gsub(pattern='(.*)[.](.*)','\\1', fullName)
  fullPathName <- paste(path, "", fullName, sep="")
  vec <- c(name, fullPathName)
  return(vec)
}

getFiles <- function(path){
  
  files <- list.files(path)
  res <- lapply(files, function (x) getNames(x, path))
  
  return(res)
}
#=============================================================================
par(mfrow=c(2,2),oma=c(0,0,2,0))
res <- getFiles("C:\\Users\\Alex\\workspace\\SoftwareSim\\simulation_results\\")
res <- getFiles("C:\\Users\\Alex\\workspace\\SoftwareSim\\simulation_results_strored\\MC\\")

maxmedias <- 4

summary <- array(0, c(30,maxmedias,length(res)))
counts <- array(0, c(30,maxmedias,length(res)))

medias <- c("EMAIL","FACETOFACE","PHONE","KNOWLEDGEBASE")

for(d in 1:length(res)){
  mediasList <- list()
  data <- c()
  data2 <- c()
  
  data <- read.csv(res[[d]][2], header=TRUE)
  
  #get indexes with efficiency > 200
  #ind <- which(with( data, data[,1]>19900))
  #if(length(ind)>0){
    #remove rows
  #  data2 <- data[ -ind, ]
  #} else {
  #  data2 <- data
  #}
  data2 <- data
  medias_number <- ncol(data2)
  rnn <- colnames(data)
  
  for(m in 2:medias_number){
    a <- c()
    a <- table(data2[,m])
    a <- as.matrix(a)
    rn <- rownames(a)
    
    means <- rep(0,30)
    maxs <- rep(0,30)
    mins <- rep(0,30)
    count <- rep(0,30)
    for(i in rn){
      f <- data2[data2[,m] %in% i,1]
      #cat(paste(c("f: ", f), collapse=" "))
      
      r <- as.numeric(as.character(i))+1
      means[r] <- mean(f)
      maxs[r] <- max(f)
      mins[r] <- min(f)
      count[r] <- length(f)
    }
    
    # create x-coordinates
    xcoords <- 1:30
    plotLineWithRange(yVal=means,yMin=mins,yMax=maxs,
                      main = rnn[m],
                      xlab = "Communication frequencies",   # Add a label on the x-axis
                      ylab = "# of steps")
    
    
    
    cat(paste(c(" rn: ", rnn), collapse=" "))
    mm <- which(medias == rnn[m]) 
    summary[,mm,d] <- means
    counts[,mm,d] <- count
    
  }
  title(res[[d]][1], outer=TRUE)
  
  while(!par('page')) plot.new()
}

#---------------------------------------------------------
par(fig=c(0, 1, 0, 1),mfrow=c(2,3),oma=c(0,0,2,0))
dat <- array(0, c(30,length(res)))
dat2 <- array(0, c(30,length(res)))
for(m in 1:maxmedias){
  
  for(f in 1:length(res)){
    
    dat[,f] <- summary[,m,f]
    dat2[,f] <- counts[,m,f]
    
  }
  matplot(dat, ylim=rev(range(dat)), type = c("b"),pch=1,col = 2:5,
          main=medias[m],
          ylab="# of steps",
          xlab="Communication frequency") #plot
  
  boxplot(dat, ylim=rev(range(dat)), main=medias[m],col = 2:5,
          xlab="Type of the team",
          ylab="# of steps")
  
  dat2 <- t(dat2)
  barplot(dat2, main=medias[m],
          col=2:5,
          ylab="Simulations per frequency",
          xlab="Communication frequency")
  
  dat <- array(0, c(30,length(res))) #clear array
  dat2 <- array(0, c(30,length(res))) #clear array
}


options(warn = oldw) #turn on warnings