#Architecture applied

##What we tried to do:
In Assignment 1 & 2 we tried to apply modularization to make the program more clean for
any future adaptations. Instead of tight coupling everything we tried to create a loose
coupling.

###Assignment1:
We tried to improve the assignment by applying an architecture modeled after MVC.
MVC however lets the view handle the input. Here we discarded this notion and altered it a bit.
Now still the controller is in charge of managing the program but instead of the view handling
the input there is a special modularization for another class: the receiver.

Main starts up the program, fires up the controller.
The controller places everything into the right position.
The receiver is the interpreter and uses the TokenStream to get tokens.
The TokenStream reads a char at the time with getToken().
Errors are handled by the controller.

We use injection to activate the controller again when new actions are needed.


###Assignment2:
Basically copies Assignment1. With some improvements as they should be.
Now the Token is in charge of setting the kind. And the TokenStream is just a reader.
Both the TokenStream as the Token do not throw any other errors. The interpreter throws the errors,
which are handled by the controller. -> now directly by print. But this could be changed.