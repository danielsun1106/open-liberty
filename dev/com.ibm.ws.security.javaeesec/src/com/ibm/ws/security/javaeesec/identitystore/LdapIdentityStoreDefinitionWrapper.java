/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.security.javaeesec.identitystore;

import java.util.Set;

import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.IdentityStore.ValidationType;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition.LdapSearchScope;

import com.ibm.websphere.ras.ProtectedString;

/**
 * A wrapper class that offers convenience methods for retrieving configuration
 * from an {@link LdapIdentityStoreDefinition} instance.
 *
 * <p/>
 * The methods in this class will evaluate any EL expressions provided in the
 * {@link LdapIdentityStoreDefinition} first and if no EL expressions are provided,
 * return the literal value instead.
 */
class LdapIdentityStoreDefinitionWrapper {

    /** The distinguished name to bind with for this IdentityStore. Will be null when set by a deferred EL expression. */
    private final String bindDn;

    /** The distinguished name's password to bind with for this IdentityStore. Will be null when set by a deferred EL expression. */
    private final ProtectedString bindDnPassword;

    /** The base distinguished name for users/callers. Will be null when set by a deferred EL expression. */
    private final String callerBaseDn;

    /** The LDAP attribute that contains the user/caller name. Will be null when set by a deferred EL expression. */
    private final String callerNameAttribute;

    /** The search base to search for the user/caller on the LDAP server. Will be null when set by a deferred EL expression. */
    private final String callerSearchBase;

    /** The LDAP filter to search for the user/caller on the LDAP server. Will be null when set by a deferred EL expression. */
    private final String callerSearchFilter;

    /** The search scope to search for the user/caller. */
    private final LdapSearchScope callerSearchScope;

    /** The LDAP attribute to use to search for group membership on a group entity. Will be null when set by a deferred EL expression. */
    private final String groupMemberAttribute;

    /** The LDAP attribute to use to search for group membership on a user/caller entity. Will be null when set by a deferred EL expression. */
    private final String groupMemberOfAttribute;

    /** The LDAP attribute to retrieve the group name from. Will be null when set by a deferred EL expression. */
    private final String groupNameAttribute;

    /** The search base to search for groups on the LDAP server. Will be null when set by a deferred EL expression. */
    private final String groupSearchBase;

    /** The LDAP filter to search for groups on the LDAP server. Will be null when set by a deferred EL expression. */
    private final String groupSearchFilter;

    /** The search scope to search for groups. Will be null when set by a deferred EL expression. */
    private final LdapSearchScope groupSearchScope;

    /** The definitions for this IdentityStore. */
    private final LdapIdentityStoreDefinition idStoreDefinition;

    /** The maximum number of results to return on a search. Will be null when set by a deferred EL expression. */
    private final Integer maxResults;

    /** The priority for this IdentityStore. Will be null when set by a deferred EL expression. */
    private final Integer priority;

    /** The read timeout for LDAP contexts. Will be null when set by a deferred EL expression. */
    private final Integer readTimeout;

    /** The URL for the LDAP server to connect to. Will be null when set by a deferred EL expression. */
    private final String url;

    /** The ValidationTypes this IdentityStore can be used for. Will be null when set by a deferred EL expression. */
    private final Set<ValidationType> useFor;

    /**
     * Create a new instance of an {@link LdapIdentityStoreDefinitionWrapper} that will provide
     * convenience methods to access configuration from the {@link LdapIdentityStoreDefinition}
     * instance.
     *
     * @param idStoreDefinition The {@link LdapIdentityStoreDefinition} to wrap.
     */
    LdapIdentityStoreDefinitionWrapper(LdapIdentityStoreDefinition idStoreDefinition) {
        /*
         * Ensure we were passed a non-null definition.
         */
        if (idStoreDefinition == null) {
            throw new IllegalArgumentException("The LdapIdentityStoreDefinition cannot be null.");
        }
        this.idStoreDefinition = idStoreDefinition;

        /*
         * Evaluate the configuration. The values will be non-null if the setting is NOT
         * a deferred EL expression. If it is a deferred EL expression, we will dynamically
         * evaluate it at call time.
         */
        this.bindDn = evaluateBindDn(true);
        this.bindDnPassword = evaluateBindDnPassword(true);
        this.callerBaseDn = evaluateCallerBaseDn(true);
        this.callerNameAttribute = evaluateCallerNameAttribute(true);
        this.callerSearchBase = evaluateCallerSearchBase(true);
        this.callerSearchFilter = evaluateCallerSearchFilter(true);
        this.callerSearchScope = evaluateCallerSearchScope(true);
        this.groupMemberAttribute = evaluateGroupMemberAttribute(true);
        this.groupMemberOfAttribute = evaluateGroupMemberOfAttribute(true);
        this.groupNameAttribute = evaluateGroupNameAttribute(true);
        this.groupSearchBase = evaluateGroupSearchBase(true);
        this.groupSearchFilter = evaluateGroupSearchFilter(true);
        this.groupSearchScope = evaluateGroupSearchScope(true);
        this.maxResults = evaluateMaxResults(true);
        this.priority = evaluatePriority(true);
        this.readTimeout = evaluateReadTimeout(true);
        this.url = evaluateUrl(true);
        this.useFor = evaluateUseFor(true);
    }

    /**
     * Evaluate and return the bindDn.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The bindDn or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private String evaluateBindDn(boolean immediateOnly) {
        return ELHelper.processString("bindDn", this.idStoreDefinition.bindDn(), immediateOnly);
    }

    /**
     * Evaluate and return the bindDnPassword.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The bindDnPassword or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private ProtectedString evaluateBindDnPassword(boolean immediateOnly) {
        String sResult = ELHelper.processString("bindDnPassword", this.idStoreDefinition.bindDnPassword(), immediateOnly, true);
        return (sResult == null) ? null : new ProtectedString(sResult.toCharArray());
    }

    /**
     * Evaluate and return the callerBaseDn.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The callerBaseDn or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private String evaluateCallerBaseDn(boolean immediateOnly) {
        return ELHelper.processString("callerBaseDn", this.idStoreDefinition.callerBaseDn(), immediateOnly);
    }

    /**
     * Evaluate and return the callerNameAttribute.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The callerNameAttribute or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private String evaluateCallerNameAttribute(boolean immediateOnly) {
        return ELHelper.processString("callerNameAttribute", this.idStoreDefinition.callerNameAttribute(), immediateOnly);
    }

    /**
     * Evaluate and return the callerSearchBase.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The callerSearchBase or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private String evaluateCallerSearchBase(boolean immediateOnly) {
        return ELHelper.processString("callerSearchBase", this.idStoreDefinition.callerSearchBase(), immediateOnly);
    }

    /**
     * Evaluate and return the callerSearchFilter.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The callerSearchFilter or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private String evaluateCallerSearchFilter(boolean immediateOnly) {
        return ELHelper.processString("callerSearchFilter", this.idStoreDefinition.callerSearchFilter(), immediateOnly);
    }

    /**
     * Evaluate and return the callerSearchScope.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The callerSearchScope or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private LdapSearchScope evaluateCallerSearchScope(boolean immediateOnly) {
        return ELHelper.processLdapSearchScope("callerSearchScopeExpression", this.idStoreDefinition.callerSearchScopeExpression(), this.idStoreDefinition.callerSearchScope(),
                                               immediateOnly);
    }

    /**
     * Evaluate and return the groupMemberAttribute.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The groupMemberAttribute or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private String evaluateGroupMemberAttribute(boolean immediateOnly) {
        return ELHelper.processString("groupMemberAttribute", this.idStoreDefinition.groupMemberAttribute(), immediateOnly);
    }

    /**
     * Evaluate and return the groupMemberOfAttribute.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The groupMemberOfAttribute or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private String evaluateGroupMemberOfAttribute(boolean immediateOnly) {
        return ELHelper.processString("groupMemberOfAttribute", this.idStoreDefinition.groupMemberOfAttribute(), immediateOnly);
    }

    /**
     * Evaluate and return the groupNameAttribute.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The groupNameAttribute or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private String evaluateGroupNameAttribute(boolean immediateOnly) {
        return ELHelper.processString("groupNameAttribute", this.idStoreDefinition.groupNameAttribute(), immediateOnly);
    }

    /**
     * Evaluate and return the groupSearchBase.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The groupSearchBase or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private String evaluateGroupSearchBase(boolean immediateOnly) {
        return ELHelper.processString("groupSearchBase", this.idStoreDefinition.groupSearchBase(), immediateOnly);
    }

    /**
     * Evaluate and return the groupSearchFilter.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The groupSearchFilter or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private String evaluateGroupSearchFilter(boolean immediateOnly) {
        return ELHelper.processString("groupSearchFilter", this.idStoreDefinition.groupSearchFilter(), immediateOnly);
    }

    /**
     * Evaluate and return the groupSearchScope.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The groupSearchScope or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private LdapSearchScope evaluateGroupSearchScope(boolean immediateOnly) {
        return ELHelper.processLdapSearchScope("groupSearchScopeExpression", this.idStoreDefinition.groupSearchScopeExpression(), this.idStoreDefinition.groupSearchScope(),
                                               immediateOnly);
    }

    /**
     * Evaluate and return the maxResults.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The maxResults or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private Integer evaluateMaxResults(boolean immediateOnly) {
        return ELHelper.processInt("maxResultsExpression", this.idStoreDefinition.maxResultsExpression(), this.idStoreDefinition.maxResults(), immediateOnly);
    }

    /**
     * Evaluate and return the priority.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The priority or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private Integer evaluatePriority(boolean immediateOnly) {
        return ELHelper.processInt("priorityExpression", this.idStoreDefinition.priorityExpression(), this.idStoreDefinition.priority(), immediateOnly);
    }

    /**
     * Evaluate and return the readTimeout.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The readTimeout or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private Integer evaluateReadTimeout(boolean immediateOnly) {
        return ELHelper.processInt("readTimeoutExpression", this.idStoreDefinition.readTimeoutExpression(), this.idStoreDefinition.readTimeout(), immediateOnly);
    }

    /**
     * Evaluate and return the url.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The url or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private String evaluateUrl(boolean immediateOnly) {
        return ELHelper.processString("url", this.idStoreDefinition.url(), immediateOnly);
    }

    /**
     * Evaluate and return the useFor.
     *
     * @param immediateOnly If true, only return a non-null value if the setting is either an
     *            immediate EL expression or not set by an EL expression. If false, return the
     *            value regardless of where it is evaluated.
     * @return The useFor or null if immediateOnly==true AND the value is not evaluated
     *         from a deferred EL expression.
     */
    private Set<ValidationType> evaluateUseFor(boolean immediateOnly) {
        return ELHelper.processUseFor(this.idStoreDefinition.useForExpression(), this.idStoreDefinition.useFor(), immediateOnly);
    }

    /**
     * Get the distinguished name to bind to the LDAP server with.
     *
     * @return The distinguished name to bind with.
     *
     * @see LdapIdentityStoreDefinition#bindDn()
     */
    String getBindDn() {
        return (this.bindDn != null) ? this.bindDn : evaluateBindDn(false);
    }

    /**
     * Get the password to bind to the LDAP server with.
     *
     * @return The password to bind with.
     *
     * @see LdapIdentityStoreDefinition#bindDnPassword()
     */
    ProtectedString getBindDnPassword() {
        return (this.bindDnPassword != null) ? this.bindDnPassword : evaluateBindDnPassword(false);
    }

    /**
     * Get the caller base distinguished name.
     *
     * @return The call base distinguished name.
     *
     * @see LdapIdentityStoreDefinition#callerBaseDn()
     */
    String getCallerBaseDn() {
        return (this.callerBaseDn != null) ? this.callerBaseDn : evaluateCallerBaseDn(false);
    }

    /**
     * Get the LDAP attribute to use to find the name of a user/caller entity.
     *
     * @return The user/caller name attribute.
     *
     * @see LdapIdentityStoreDefinition#callerNameAttribute()
     */
    String getCallerNameAttribute() {
        return (this.callerNameAttribute != null) ? this.callerNameAttribute : evaluateCallerNameAttribute(false);
    }

    /**
     * Get the search base to search for user/caller entities.
     *
     * @return The user/caller search base.
     *
     * @see LdapIdentityStoreDefinition#callerSearchBase()
     */
    String getCallerSearchBase() {
        return (this.callerSearchBase != null) ? this.callerSearchBase : evaluateCallerSearchBase(false);
    }

    /**
     * Get the search filter to search for user/caller entities.
     *
     * @return The user/caller search filter.
     *
     * @see LdapIdentityStoreDefinition#callerSearchFilter()
     */
    String getCallerSearchFilter() {
        return (this.callerSearchFilter != null) ? this.callerSearchFilter : evaluateCallerSearchFilter(false);
    }

    /**
     * Get the search scope to search for user/caller entities.
     *
     * @return The user/caller search scope.
     *
     * @see LdapIdentityStoreDefinition#callerSearchScope()
     * @see LdapIdentityStoreDefinition#callerSearchScopeExpression()
     */
    LdapSearchScope getCallerSearchScope() {
        return this.callerSearchScope != null ? this.callerSearchScope : evaluateCallerSearchScope(false);
    }

    /**
     * Get the LDAP attribute to use to find group membership on a group entity.
     *
     * @return The group member attribute.
     *
     * @see LdapIdentityStoreDefinition#groupMemberAttribute()
     */
    String getGroupMemberAttribute() {
        return (this.groupMemberAttribute != null) ? this.groupMemberAttribute : evaluateGroupMemberAttribute(false);
    }

    /**
     * Get the LDAP attribute to use to find group membership on a user/caller entity.
     *
     * @return The group member of attribute.
     *
     * @see LdapIdentityStoreDefinition#groupMemberOfAttribute()
     */
    String getGroupMemberOfAttribute() {
        return (this.groupMemberOfAttribute != null) ? this.groupMemberOfAttribute : evaluateGroupMemberOfAttribute(false);
    }

    /**
     * Get the LDAP attribute to use to find the name of a group entity.
     *
     * @return The group name attribute.
     *
     * @see LdapIdentityStoreDefinition#groupNameAttribute()
     */
    String getGroupNameAttribute() {
        return (this.groupNameAttribute != null) ? this.groupNameAttribute : evaluateGroupNameAttribute(false);
    }

    /**
     * Get the search base to search for group entities.
     *
     * @return The group search base.
     *
     * @see LdapIdentityStoreDefinition#groupSearchBase()
     */
    String getGroupSearchBase() {
        return (this.groupSearchBase != null) ? this.groupSearchBase : evaluateGroupSearchBase(false);
    }

    /**
     * Get the search filter to search for group entities.
     *
     * @return The group search filter.
     *
     * @see LdapIdentityStoreDefinition#groupSearchFilter()
     */
    String getGroupSearchFilter() {
        return (this.groupSearchFilter != null) ? this.groupSearchFilter : evaluateGroupSearchFilter(false);
    }

    /**
     * Get the search scope to search for group entities.
     *
     * @return The group search scope.
     *
     * @see LdapIdentityStoreDefinition#groupSearchScope()
     * @see LdapIdentityStoreDefinition#groupSearchScopeExpression()
     */
    LdapSearchScope getGroupSearchScope() {
        return (this.groupSearchScope != null) ? this.groupSearchScope : evaluateGroupSearchScope(false);
    }

    /**
     * Get the maximum number of results to return from a search.
     *
     * @return The maximum number of results.
     *
     * @see LdapIdentityStoreDefinition#maxResults()
     * @see LdapIdentityStoreDefinition#maxResultsExpression()
     */
    int getMaxResults() {
        return (this.maxResults != null) ? this.maxResults : evaluateMaxResults(false);
    }

    /**
     * Get the priority for the {@link IdentityStore}.
     *
     * @return The priority.
     *
     * @see LdapIdentityStoreDefinition#priority()
     * @see LdapIdentityStoreDefinition#priorityExpression()
     */
    int getPriority() {
        return (this.priority != null) ? this.priority : evaluatePriority(false);
    }

    /**
     * Get the read timeout for the {@link IdentityStore}.
     *
     * @return The read timeout.
     *
     * @see LdapIdentityStoreDefinition#readTimeout()
     * @see LdapIdentityStoreDefinition#readTimeoutExpression()
     */
    int getReadTimeout() {
        return (this.readTimeout != null) ? this.readTimeout : evaluateReadTimeout(false);
    }

    /**
     * Get the URL of the LDAP server to bind to.
     *
     * @return The URL of the LDAP server.
     *
     * @see LdapIdentityStoreDefinition#url()
     */
    String getUrl() {
        return (this.url != null) ? this.url : evaluateUrl(false);
    }

    /**
     * Get the useFor for the {@link IdentityStore}.
     *
     * @return The useFor.
     *
     * @see LdapIdentityStoreDefinition#useFor()
     * @see LdapIdentityStoreDefinition#useForExpression()
     */
    Set<ValidationType> getUseFor() {
        return (this.useFor != null) ? this.useFor : evaluateUseFor(false);
    }
}
