# Decisions & development steps

1.  my first step was to get the project to build. Upon importing into AS I noticed that the libraries stack
    was a bit outdated, so I spent some time upgrading almost everything to the latest version (including AGP)
2.  I needed to look into koin, since I had zero experience with it (only used the dagger lib.) and I needed to be sure how to provide dependencies into defined ViewModels
3.  after that I went straight for the 3rd requirement (extract network into its module) following the same principles as in the storage module
4.  in the storage module I added flow providing overloads to existing functions so that the ui layer would have some sort of reactive access to the data including sort mode persistence
5.  in the detail screen I decided to create a serializable copy of the domain Todo type which is useful for saving&restoring ui state with the help of SavedStateHandle.
6.  in the lists screen I decided to use a form of event reporting in form of the Channel to notify the ui in case the "sync" failed while keeping the screens state in a separate StateFlow
