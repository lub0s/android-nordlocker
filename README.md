# NordLocker - Android Tech Task

Hello, in this task you will finish a ToDo List app :D

We know that with our busy schedules we can't do all that is required, thinking about it
we decide to provide some implementations that you can start with, for example Adapters for list and dependency injection.

All the tech stack used here is used by us in our daily tasks, but feel free to change current implementation to use 
any technology that you feel comfortable with :D, just write about it in the doc task.
And to finalize, don't worry about layouts to be beautiful, we are not going to evaluate that :)

What is already done:
* Domain and Storage are modularized.
* Navigation between fragments.
* Network is already integrated.

So what will be your tasks?

(1) Implement the main ToDo List screen
  - Fetch from the API and show to the user.
  - User can click in a list item and navigate to Details (details screen) of that ToDo
  - Make the list available offline.
  - Sort list: Users can be able to sort the list to see recently updated or not completed tasks at the top.
    
(2) Implement a ToDo details screen
  - Show the details from the ToDo to the user.
  - User can be able to edit the information.
  - User can be able to complete the ToDo.
    
(3) Modularization
  - Modularize the network part so we can separate this part from the rest of the application.
    
(4) Write a small doc about your development steps, decisions and reasons to go for specific approach.

PS: If you feel that you can do more, you can do one of the following bonus tasks (don't need to be in the order):
- Add list pagination support.
- Implement more sort options like: completed, nearest due date.
- Write some unit tests (don't need to write UI tests).
- Improve code that is already done.
- Any other task that you judge it will be nice to have :D

Well, that's it, have fun and if do you have any question, please reach us :D

## Tech stack: 
* Network - <a href="https://ktor.io/">Ktor</a>
* Database - <a href="https://developer.android.com/training/data-storage/room">Room</a>
* Concurrency - <a href="https://kotlinlang.org/docs/coroutines-overview.html">Coroutines</a>
* Dependency injection - <a href="https://insert-koin.io/">Koin</a>
* Navigation - <a href="https://developer.android.com/guide/navigation">Navigation</a>
* Architecture - <a href="https://developer.android.com/jetpack/guide">MVVM</a>