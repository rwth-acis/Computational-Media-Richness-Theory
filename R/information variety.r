#uniform distribution - random topics
plot.x <- c()
plot.y <- c()

for(j in 1:5){
  discussedTopics <- c()
  #generate uniform random sequence from 0 to 100 3 times
  communication <- sample(rep(1:100, 3))
  
  ind<-1
  for(i in 1:length(communication)){
    if(!is.element(communication[i], discussedTopics) ){
      discussedTopics[ind]<-communication[i]
      ind <- ind + 1
    }
    plot.x <- c(plot.x,i)
    plot.y <- c(plot.y,ind) 
  }
}

library(car)
COLORS <- palette()
COLORS[3] <- rgb(0,100,0,100,maxColorValue=255)
scatterplotMatrix(cbind(plot.x, plot.y), smooth=TRUE, col=COLORS, 
                  diagonal=c("histogram"),
                  var.labels=c("Number of communication","Collected uniquie topics"),
                   main="Uniform distribution - Random topics")

#--------------------------------------------------------------------

#uniform distribution - predefined set of useful topics
plot.x <- c()
plot.y <- c()
usefulTopics <- c(1:10)

for(j in 1:5){
  discussedTopics <- c()
  #generate uniform random sequence from 0 to 100 3 times
  communication <- sample(rep(1:100, 3))
  
  ind<-1
  for(i in 1:length(communication)){
    if(!is.element(communication[i], discussedTopics) && is.element(communication[i], usefulTopics)){
      discussedTopics[ind]<-communication[i]
      ind <- ind + 1
    }
    plot.x <- c(plot.x,i)
    plot.y <- c(plot.y,ind) 
  }
}

library(car)
COLORS <- palette()
COLORS[3] <- rgb(0,100,0,100,maxColorValue=255)
scatterplotMatrix(cbind(plot.x, plot.y), smooth=TRUE, col=COLORS, 
                  diagonal=c("histogram"),
                  var.labels=c("Number of communication","Collected useful topics"),
                  main="Uniform distribution - Limited set of useful topics")

#--------------------------------------------------------------------

#normal distribution - predefined set of useful topics
plot.x <- c()
plot.y <- c()
usefulTopics <- c(45:54)

for(j in 1:5){
  x <- pretty(c(1,100), 100)
  y <- dnorm(x, m=50, sd=15)
  
  discussedTopics <- c()
  #generate normal distribution random sequence from 0 to 100 3 times
  communication <- rep(sample(x, 100, prob = y, replace = T), 3)

  ind<-1
  for(i in 1:length(communication)){
    if(!is.element(communication[i], discussedTopics) && is.element(communication[i], usefulTopics)){
      discussedTopics[ind]<-communication[i]
      ind <- ind + 1
    }
    plot.x <- c(plot.x,i)
    plot.y <- c(plot.y,ind) 
  }
}

plot(x,y, type='l', xlab="Topics id's", ylab="Density", main="Topics id's Normal Distribution")

library(car)
COLORS <- palette()
COLORS[3] <- rgb(0,100,0,100,maxColorValue=255)
scatterplotMatrix(cbind(plot.x, plot.y), smooth=TRUE, col=COLORS, 
                  diagonal=c("histogram"),
                  var.labels=c("Number of communication","Collected useful topics"),
                  main="Normal distribution - Limited set of useful topics")