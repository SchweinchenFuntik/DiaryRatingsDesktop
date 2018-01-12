package ua.funtik.ratings

import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val critical by cssclass()
        val fieldSetLogin by cssclass()
        val buttonLogin by cssclass()
    }

    init {
//        label and heading {
//            padding = box(10.px)
//            fontSize = 20.px
//            fontWeight = FontWeight.BOLD
//        }

        buttonLogin {
            backgroundInsets += box(0.px)
            padding = box(20.px)
            minWidth = 300.px
            prefWidth = 300.px
            maxWidth = 300.px
        }
    }
}