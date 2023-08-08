# CSC2002S
// Omolemo Modise
// MDSOMO001
// PCP1

This programme uses a Monte Carlo approach to determine the minimum (lowest point) of a two-dimensional mathematical function f(x,y) within a given range - this is an optimisation problem. Consider the 2D function to be the height of a terrain, and the program's duty to identify the lowest point inside a specified rectangle area.
This area is represented as a discrete grid of points with equal spacing. Due to the size of this grid and the enormous expense of computing the function for all of the points, the Monte Carlo algorithm instead takes a probabilistic approach to determining the minimum of the function without computing all of the values in the grid. This is accomplished by a series of searches, as seen below.
A starting grid point is chosen at random for each search, and its height is determined. The search then attempts to proceed downhill from that location by computing the height of each of the four adjacent grid points and then moving to the one with the lowest value. It then attempts to migrate downhill from this new grid point in the same manner. The search is repeated until it is unable to identify a downhill direction: all of the nearby points have a higher value than the current position. The search has reached a local minimum and has come to an end.
With enough distinct searches, this technique has a high likelihood of finding a "global" minimum (the minimum point in the region of interest). More searches enhance the likelihood of discovering the global minimum, but they also raise the computing cost.
