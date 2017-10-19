package com.om.snipit

import com.om.snipit.database.DBHelper
import com.om.snipit.models.Book
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync

class BooksActivityPresenter {
  fun getBooks(view: BooksActivityView, dbHelper: DBHelper) = dbHelper.doAsync {
    bg {
      val allBooks = ArrayList<Book>()

      dbHelper.use {
        select(DBHelper.bookTableName).parseList(object : MapRowParser<List<Book>> {

          override fun parseRow(columns: Map<String, Any?>): ArrayList<Book> {

            val id = columns.getValue("id") as Int
            val title = columns.getValue("title") as String
            val author = columns.getValue("author") as String
            val image_path = columns.getValue("image_path") as String
            val date_added = columns.getValue("date_added") as String
            val color_code = columns.getValue("color_code") as Int
            val order = columns.getValue("order") as Int

            val book = Book(id, title, author, image_path, date_added, color_code, order)

            allBooks.add(book)

            return allBooks
          }
        })
      }

      view.displayBooks(allBooks)

    }
  }

  fun insertBook(book: Book, dbHelper: DBHelper) = dbHelper.doAsync {
    bg {
      dbHelper.use {
        insert(DBHelper.bookTableName,
            "title" to book.title,
            "author" to book.author,
            "image_path" to book.image_path,
            "date_added" to book.date_added,
            "color_code" to book.color_code,
            "list_order" to book.list_order)
      }
    }
  }

  interface BooksActivityView {
    fun displayBooks(books: ArrayList<Book>)

  }
}