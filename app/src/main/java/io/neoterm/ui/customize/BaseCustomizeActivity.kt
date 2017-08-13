package io.neoterm.ui.customize

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import io.neoterm.R
import io.neoterm.backend.TerminalSession
import io.neoterm.frontend.preference.NeoTermPath
import io.neoterm.frontend.shell.ShellParameter
import io.neoterm.frontend.tinyclient.BasicSessionCallback
import io.neoterm.frontend.tinyclient.BasicViewClient
import io.neoterm.utils.TerminalUtils
import io.neoterm.view.TerminalView
import io.neoterm.view.eks.ExtraKeysView

/**
 * @author kiva
 */
@SuppressLint("Registered")
open class BaseCustomizeActivity : AppCompatActivity() {
    lateinit var terminalView: TerminalView
    lateinit var viewClient: BasicViewClient
    lateinit var sessionCallback: BasicSessionCallback
    lateinit var session: TerminalSession
    lateinit var extraKeysView: ExtraKeysView

    fun initCustomizationComponent(layoutId: Int) {
        setContentView(layoutId)

        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        terminalView = findViewById<TerminalView>(R.id.terminal_view)
        extraKeysView = findViewById<ExtraKeysView>(R.id.custom_extra_keys)
        viewClient = BasicViewClient(terminalView)
        sessionCallback = BasicSessionCallback(terminalView)
        TerminalUtils.setupTerminalView(terminalView, viewClient)

        val parameter = ShellParameter()
                .executablePath("${NeoTermPath.USR_PATH}/bin/applets/echo")
                .arguments(arrayOf("echo", "Hello NeoTerm."))
                .callback(sessionCallback)
                .systemShell(false)

        session = TerminalUtils.createShellSession(this, parameter)
        terminalView.attachSession(session)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}